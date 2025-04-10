package com.omgservers.schema.model.runtimeMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.RuntimeCreatedMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class RuntimeMessageModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenRuntimeCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var idempotencyKey = "idempotency_value";
        final var runtimeId = 2000L;
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = MessageQualifierEnum.RUNTIME_CREATED;
        final var body = new RuntimeCreatedMessageBodyDto(RuntimeConfigDto.create(TenantVersionConfigDto.create()));
        final var deleted = false;

        final var runtimeCommandModel = new RuntimeMessageModel(id,
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

        final var runtimeCommandObject = objectMapper.readValue(runtimeCommandString, RuntimeMessageModel.class);

        assertEquals(runtimeCommandModel, runtimeCommandObject);
    }
}