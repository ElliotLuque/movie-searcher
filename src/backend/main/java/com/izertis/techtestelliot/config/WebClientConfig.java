package com.izertis.techtestelliot.config;

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

import java.net.URI;

@Configuration
public class WebClientConfig {

    @Bean("movieWebClient")
    @ConditionalOnProperty(name = "movies.provider",
            havingValue = "tmdb",
            matchIfMissing = true
    )
    public WebClient tmdbWebClient(
            @Value("${tmdb.base-url") String baseUrl,
            @Value("${tmdb.api-key") String apiKey
    ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .filter(ExchangeFilterFunctions.statusError(HttpStatusCode::isError,
                        r -> new RuntimeException("Error al llamar a la API de TMDB"))
                )
                .build();
    }

    @Bean("movieWebClient")
    @ConditionalOnProperty(name = "movies.provider",
            havingValue = "omdb"
    )
    public WebClient omdbWebClient(@Value("${omdb.base-url") String baseUrl,
                                   @Value("${omdb.api-key") String apiKey
    ) {
        ExchangeFilterFunction apikeyFilter =
                (request, next) -> {
                    URI original = request.url();

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
                        r -> new RuntimeException("Error al llamar a la API de OMDB"))
                )
                .build();
    }
}
