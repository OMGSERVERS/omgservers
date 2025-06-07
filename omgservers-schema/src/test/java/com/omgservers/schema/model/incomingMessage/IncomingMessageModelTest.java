package com.omgservers.schema.model.incomingMessage;

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
class IncomingMessageModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenIncomingMessageModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var qualifier = MessageQualifierEnum.CLIENT_GREETED;
        final var tenantId = 3000L;
        final var versionId = 4000L;
        final var versionCreated = Instant.now();
        final var body = new ClientGreetedMessageBodyDto(tenantId, versionId, versionCreated);

        final var incomingMessageModel = new IncomingMessageModel(id,
                qualifier,
                body);
        log.info("Incoming message model, {}", incomingMessageModel);

        final var incomingMessageString = objectMapper.writeValueAsString(incomingMessageModel);
        log.info("Deserialized value, {}", incomingMessageString);

        final var incomingMessageObject = objectMapper.readValue(incomingMessageString, IncomingMessageModel.class);

        assertEquals(incomingMessageModel, incomingMessageObject);
    }
}