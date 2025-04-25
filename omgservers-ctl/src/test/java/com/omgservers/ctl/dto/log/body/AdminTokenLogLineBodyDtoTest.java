package com.omgservers.ctl.dto.log.body;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.dto.key.KeyEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

@QuarkusTest
class AdminTokenLogLineBodyDtoTest {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void test() {
        final var logLine = new AdminTokenLogLineBodyDto();
        final var map = objectMapper.convertValue(logLine, new TypeReference<Map<KeyEnum, String>>() {
        });
    }
}