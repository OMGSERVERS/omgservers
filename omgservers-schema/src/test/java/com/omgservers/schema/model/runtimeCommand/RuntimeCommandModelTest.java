package com.omgservers.schema.model.runtimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtimeCommand.body.InitRuntimeCommandBodyDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class RuntimeCommandModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenRuntimeCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var idempotencyKey = "idempotency_value";
        final var runtimeId = 2000L;
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = RuntimeCommandQualifierEnum.INIT_RUNTIME;
        final var body = new InitRuntimeCommandBodyDto(RuntimeConfigDto.create());
        final var deleted = false;

        final var runtimeCommandModel = new RuntimeCommandModel(id,
                idempotencyKey,
                runtimeId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Runtime command model, {}", runtimeCommandModel);

        final var runtimeCommandString = objectMapper.writeValueAsString(runtimeCommandModel);
        log.info("Deserialized value, {}", runtimeCommandString);

        final var runtimeCommandObject = objectMapper.readValue(runtimeCommandString, RuntimeCommandModel.class);

        assertEquals(runtimeCommandModel, runtimeCommandObject);
    }
}