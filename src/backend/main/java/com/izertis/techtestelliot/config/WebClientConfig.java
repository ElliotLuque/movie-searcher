package com.izertis.techtestelliot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
public class WebClientConfig {

    @Bean("tmdbWebClient")
    @ConditionalOnProperty(name = "movies.provider",
            havingValue = "tmdb",
            matchIfMissing = true
    )
    public WebClient tmdbWebClient(
            @Value("${movies.tmdb.base-url}") String baseUrl,
            @Value("${movies.tmdb.api-key}") String apiKey
    ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .filter(ExchangeFilterFunctions.statusError(HttpStatusCode::isError,
                        r -> new RuntimeException("Error calling TMDB API"))
                )
                .build();
    }

    @Bean("omdbWebClient")
    @ConditionalOnProperty(name = "movies.provider",
            havingValue = "omdb"
    )
    public WebClient omdbWebClient(@Value("${movies.omdb.base-url}") String baseUrl,
                                   @Value("${movies.omdb.api-key}") String apiKey
    ) {
        ExchangeFilterFunction apikeyFilter =
                (request, next) -> {
                    URI original = request.url();
                    if (original.getQuery() != null && original.getQuery().contains("apikey=")) {
                        return next.exchange(request);
                    }

                    URI withKey = UriComponentsBuilder.fromUri(original)
                            .queryParam("apikey", apiKey)
                            .build(true)
                            .toUri();

                    ClientRequest newRequest = ClientRequest.from(request)
                            .url(withKey)
                            .build();

                    return next.exchange(newRequest);
                };

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .filter(apikeyFilter)
                .filter(ExchangeFilterFunctions.statusError(HttpStatusCode::isError,
                        r -> new RuntimeException("Error calling OMDB API"))
                )
                .build();
    }
}
