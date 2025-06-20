package com.izertis.techtestelliot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI movieOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Searcher API")
                        .version("v1")
                        .contact(new Contact().name("Elliot").email("elliotluque@gmail.com"))
                );
    }
}
