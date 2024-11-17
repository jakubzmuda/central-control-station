package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.Api;
import com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.Port;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.*;

@Configuration
public class TestApplication {

    @Bean
    Api api(Port port) {
        return new Api(port);
    }

    @Bean
    Port port(Environment environment) {
        return new Port(environment);
    }

    @Bean
    Clock clock() {
        return Clock.fixed(
                Instant.ofEpochSecond(LocalDateTime.of(2024, 11, 11, 10, 0)
                        .toEpochSecond(ZoneOffset.UTC)), ZoneId.of("UTC"));
    }
}