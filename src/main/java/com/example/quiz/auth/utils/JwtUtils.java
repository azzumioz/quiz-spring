package com.example.quiz.auth.utils;

import com.example.quiz.auth.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
@Component
public class JwtUtils {

    private final String CLAIM_USER_KEY = "user";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.login-expiration}")
    private int loginExpiration;

    @Value("${jwt.reset-pass-expiration}")
    private int resetExpiration;

    public String createEmailResetToken(User user) {
        return createToken(user, resetExpiration);
    }

    private String createToken(User user, int duration) {
        Date currentDate = new Date();

        user.setPassword(null);

        Map claims = new HashMap<String, Object>();
        claims.put(CLAIM_USER_KEY, user);
        claims.put(Claims.SUBJECT, user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + duration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateJwt(User user) {
        Date currentDate = new Date();

        Map claims = new HashMap<String, Object>();

        claims.put(CLAIM_USER_KEY, user);
        claims.put(Claims.SUBJECT, user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + loginExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validate(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            log.log(Level.SEVERE, "Invalid JWT token: ", jwt);
        } catch (ExpiredJwtException e) {
            log.log(Level.SEVERE, "JWT token is expired: ", jwt);
        } catch (UnsupportedJwtException e) {
            log.log(Level.SEVERE, "JWT token is unsupported: ", jwt);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "JWT claims string is empty: ", jwt);
        }
        return false;
    }

    public User getUser(String jwt) {
        Map map = (Map) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().get(CLAIM_USER_KEY);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(map, User.class);
        return user;
    }

}
