package com.izertis.techtestelliot.config.security.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ProblemAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public ProblemAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problem.setInstance(URI.create(exchange.getRequest().getPath().value()));

        byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(problem);
        } catch (Exception e) {
            bytes = new byte[0];
        }

        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.valueOf("application/problem+json"));
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
