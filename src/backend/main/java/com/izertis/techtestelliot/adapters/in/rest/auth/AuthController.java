package com.izertis.techtestelliot.adapters.in.rest.auth;

import com.izertis.techtestelliot.config.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwt;

    @PostMapping("/login")
    public Mono<ResponseEntity<Void>> login(ServerWebExchange exchange) {
        String token = jwt.generateToken();

        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(jwt.getExpiration())
                .build();

        exchange.getResponse().addCookie(cookie);
        return Mono.just(ResponseEntity.noContent().build());
    }
}
