package com.rental.api.configuration.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder().failOnUnknownProperties(false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // SpringBoot default
                ;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return objectMapperBuilder().build();
    }

}
