package com.omgservers.schema.model.runtimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtimeCommand.body.AssignClientRuntimeCommandBodyDto;
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
        final var runtimeId = 2000L;
        final var idempotencyKey = "idempotency_value";
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = RuntimeCommandQualifierEnum.ASSIGN_CLIENT;
        final var clientId = 3000L;
        final var groupName = "players";
        final var body = new AssignClientRuntimeCommandBodyDto(clientId, groupName);
        final var deleted = false;

        final var runtimeCommandModel = new RuntimeCommandModel(id,
                idempotencyKey,
                runtimeId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Matchmaker command model, {}", runtimeCommandModel);

        final var messageString = objectMapper.writeValueAsString(runtimeCommandModel);
        log.info("Deserialized value, {}", messageString);

        final var runtimeCommandObject = objectMapper.readValue(messageString, RuntimeCommandModel.class);

        assertEquals(runtimeCommandModel, runtimeCommandObject);
    }
}