package com.omgservers.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectMapperConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper objectMapper) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}