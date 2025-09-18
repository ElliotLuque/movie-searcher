package com.elliot.moviesearcher.config.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwt;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String bearer = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (bearer != null && bearer.startsWith("Bearer ")) {
            token = bearer.substring(7);
        } else {
            HttpCookie cookie = exchange.getRequest().getCookies().getFirst("auth_token");
            if (cookie != null) token = cookie.getValue();
        }

        if (token == null) {
            return chain.filter(exchange);
        }

        if (!jwt.isValid(token)) {
            return Mono.error(new AuthenticationCredentialsNotFoundException("Invalid or expired token"));
        }

        var auth = new UsernamePasswordAuthenticationToken(
                token,
                token,
                AuthorityUtils.NO_AUTHORITIES);

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }}
