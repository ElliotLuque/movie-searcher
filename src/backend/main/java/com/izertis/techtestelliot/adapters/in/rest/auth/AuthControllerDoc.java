package com.izertis.techtestelliot.adapters.in.rest.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Tag(name = "Auth", description = "Authentication endpoints")
public interface AuthControllerDoc {

    @SuppressWarnings("unused")
    @Operation(
            summary = "Login",
            description = "Generates a JWT and sets it in a secure HTTP-only cookie for client authentication.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Login successful, cookie set"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Internal Server Error",
                                                        "status": 500,
                                                        "detail": "Unexpected error during token generation",
                                                        "instance": "/api/v1/auth/login"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    Mono<ResponseEntity<Void>> login(ServerWebExchange exchange);


    @SuppressWarnings("unused")
    @Operation(
            summary = "Check auth status",
            description = "Verifies whether the HttpOnly cookie contains a valid JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Currently logged-in"
                    ),
                    @ApiResponse(
                    responseCode = "401",
                    description = "No session or token invalid",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples  = @ExampleObject(
                                    value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Unauthorized",
                                                        "status": "401",
                                                        "detail": "Not Authenticated",
                                                        "instance": "/api/v1/movies"
                                                      }
                                                    """
                            )
                    )
            )
            }
    )
    Mono<ResponseEntity<Object>> status(@AuthenticationPrincipal Mono<String> principalMono);

    @SuppressWarnings("unused")
    @Operation(
            summary = "Logout",
            description = "Clears the HttpOnly cookie.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Logged out successfully"
                    ),
                    @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated yet",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples  = @ExampleObject(
                                    value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Unauthorized",
                                                        "status": "401",
                                                        "detail": "Not Authenticated",
                                                        "instance": "/api/v1/movies"
                                                      }
                                                    """
                            )
                    )
            )
            }
    )
    Mono<ResponseEntity<Void>> logout(ServerWebExchange exchange);
}
