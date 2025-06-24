package com.izertis.techtestelliot.config.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    @Getter
    private Duration expiration;

    @Value("${jwt.cookie.secure}")
    private boolean cookieSecure;

    @Value("${jwt.cookie.same-site}")
    private String cookieSameSite;

    public String generateToken() {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject("guest")
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public boolean isValid(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public ResponseCookie buildAuthCookie(String token) {
        return ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .path("/")
                .maxAge(expiration)
                .build();
    }

    public ResponseCookie buildLogoutCookie() {
        return ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
    }
}
