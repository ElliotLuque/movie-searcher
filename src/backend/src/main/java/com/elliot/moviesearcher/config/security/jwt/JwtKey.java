package com.elliot.moviesearcher.config.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtKey {

    @Bean
    public SecretKey secretKey(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = secret.matches("^[A-Za-z0-9+/=]+$") && secret.length() % 4 == 0
                ? Decoders.BASE64.decode(secret)
                : secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
