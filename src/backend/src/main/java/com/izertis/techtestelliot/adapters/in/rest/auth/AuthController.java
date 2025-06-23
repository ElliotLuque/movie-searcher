package com.izertis.techtestelliot.adapters.in.rest.auth;

import com.izertis.techtestelliot.config.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDoc {

    private final JwtUtil jwt;

    @Override
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

    @Override
    @PostMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(ServerWebExchange exchange) {
        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        exchange.getResponse().addCookie(cookie);

        return Mono.just(ResponseEntity.noContent().build());
    }

    @Override
    @GetMapping("/status")
    public Mono<ResponseEntity<Object>> status(
            @AuthenticationPrincipal Mono<String> principalMono
    ) {
        return principalMono
                .map(u-> ResponseEntity.noContent().build())
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
