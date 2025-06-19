package com.izertis.techtestelliot.adapters.in.rest.error;

import com.izertis.techtestelliot.domain.exception.MovieNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, ServerWebExchange exchange) {
        List<ProblemDetail.Violation> violations = ex.getConstraintViolations().stream()
                .map(v ->  new ProblemDetail.Violation(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());

        var problem = ProblemDetail.withViolations(
                "https://www.example.com/problems/validation-error",
                "Invalid request",
                400,
                "Validation errors found: " + violations.size(),
                exchange.getRequest().getPath().value(),
                violations
        );

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(problem);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleMovieNotFound(
            MovieNotFoundException ex, ServerWebExchange exchange) {

        var problem = ProblemDetail.withoutViolations(
                "https://example.com/problems/not-found",
                "Not Found",
                404,
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return ResponseEntity
                .status(404)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(problem);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotAllowed(
            MethodNotAllowedException ex,
            ServerWebExchange exchange
            ) {

        String allowed = ex.getSupportedMethods().stream()
                .map(HttpMethod::name)
                .collect(Collectors.joining(", "));

        ProblemDetail problem = ProblemDetail.withoutViolations(
                "https://example.com/problems/method-not-allowed",
                "HTTP Method not allowed",
                405,
                "Method '" + ex.getHttpMethod() + "' is not allowed for this resource. Valid methods: " + allowed + ".",
                exchange.getRequest().getURI().getPath()
        );

        return ResponseEntity
                .status(405)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericError(
            Exception ex, ServerWebExchange exchange) {

        var problem = ProblemDetail.withoutViolations(
                "https://example.com/problems/internal-error",
                "Internal Server Error",
                500,
                "Unexpected error: " + ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return ResponseEntity
                .status(500)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(problem);
    }
}