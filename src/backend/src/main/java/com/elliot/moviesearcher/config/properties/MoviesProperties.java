package com.elliot.moviesearcher.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "movies")
public class MoviesProperties {

    private String provider = "omdb";

    private final Tmdb tmdb = new Tmdb();
    private final Omdb omdb = new Omdb();

    @Data
    public static class Tmdb {
        private String baseUrl;
        private String apiKey;
        private String language = "en-US";
    }

    @Data
    public static class Omdb {
        private String baseUrl;
        private String apiKey;
    }
}
