package com.izertis.techtestelliot.config.security.jwt;

import com.izertis.techtestelliot.config.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public String generateToken() {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject("guest")
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtProperties.getExpiration().toMillis()))
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
                .secure(jwtProperties.getCookie().isSecure())
                .sameSite(jwtProperties.getCookie().getSameSite())
                .path("/")
                .maxAge(jwtProperties.getExpiration())
                .build();
    }

    public ResponseCookie buildLogoutCookie() {
        return ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(jwtProperties.getCookie().isSecure())
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
    }
}
