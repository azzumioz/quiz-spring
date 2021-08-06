package com.example.quiz.auth.filter;

import com.example.quiz.auth.entity.User;
import com.example.quiz.auth.exception.JwtCommonException;
import com.example.quiz.auth.service.UserDetailsImpl;
import com.example.quiz.auth.utils.CookieUtil;
import com.example.quiz.auth.utils.JwtUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    private JwtUtils jwtUtils;
    private CookieUtil cookieUtil;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    public void setCookieUtil(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }

    private List<String> permitURL = Arrays.asList(
            "register",
            "login",
            "activate-account",
            "resend-activate-email",
            "send-reset-password-email",
            "swagger-ui",
            "v3"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean isRequestToPublicPage = permitURL.stream().anyMatch(s -> httpServletRequest.getRequestURI().toLowerCase().contains(s));
        if (!isRequestToPublicPage &&
//                SecurityContextHolder.getContext().getAuthentication() == null &&
                !httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())
        ) {
            String jwt = null;

            if (httpServletRequest.getRequestURI().contains("update-password")) {
                jwt = getJwtFromHeader(httpServletRequest);
            } else {
                jwt = cookieUtil.getCookieAccessToken(httpServletRequest);
            }

            if (jwt != null) {
                if (jwtUtils.validate(jwt)) {
                    User user = jwtUtils.getUser(jwt);
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new JwtCommonException("jwt validate exception");
                }
            } else {
                throw new AuthenticationCredentialsNotFoundException("token not found");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
