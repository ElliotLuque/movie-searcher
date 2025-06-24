package com.izertis.techtestelliot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Cookie cookie;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration expiration;

    @Data
    public static class Cookie {
        private boolean secure;
        private String sameSite;
    }
}
