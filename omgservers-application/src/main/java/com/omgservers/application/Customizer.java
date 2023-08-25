package com.omgservers.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Customizer implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}