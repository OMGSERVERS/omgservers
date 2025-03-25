package com.omgservers.schema.model.clientMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.ClientGreetedMessageBodyDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class ClientMessageModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenClientMessageModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var idempotencyKey = "idempotency_value";
        final var clientId = 2000L;
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = MessageQualifierEnum.CLIENT_GREETED;
        final var tenantId = 3000L;
        final var versionId = 4000L;
        final var body = new ClientGreetedMessageBodyDto(tenantId, versionId, created);
        final var deleted = false;

        final var clientMessageModel = new ClientMessageModel(id,
                idempotencyKey,
                clientId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Client message model, {}", clientMessageModel);

        final var clientMessageString = objectMapper.writeValueAsString(clientMessageModel);
        log.info("Deserialized value, {}", clientMessageString);

        final var clientMessageObject = objectMapper.readValue(clientMessageString, ClientMessageModel.class);

        assertEquals(clientMessageModel, clientMessageObject);
    }
}