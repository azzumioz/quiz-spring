package com.example.quiz.auth.utils;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {

    @Value("${cookie.jwt.name}")
    private String cookieJwtName;

    @Value("${cookie.jwt.max-age}")
    private int cookieAccessTokenDuration;

    @Value("${cookie.domain}")
    private String cookieAccessTokenDomain;

    public HttpCookie createJwtCookie(String jwt) {
        return ResponseCookie.
                from(cookieJwtName, jwt)
                .maxAge(cookieAccessTokenDuration)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieAccessTokenDomain)
                .path("/")
                .build();
    }

    public String getCookieAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieJwtName.equals((cookie.getName()))) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public HttpCookie deleteJwtCookie() {
        return ResponseCookie.
                from(cookieJwtName, null)
                .maxAge(0)
                .sameSite(SameSiteCookies.NONE.getValue())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
    }

}
