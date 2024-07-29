package com.omgservers.schema.model.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class MessageModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenMessageModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var qualifier = MessageQualifierEnum.SERVER_WELCOME_MESSAGE;
        final var tenantId = 2000L;
        final var versionId = 3000L;
        final var body = new ServerWelcomeMessageBodyModel(tenantId, versionId, Instant.now());

        final var messageModel = new MessageModel(id,
                qualifier,
                body);
        log.info("Message model, {}", messageModel);

        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("Deserialized value, {}", messageString);

        final var messageObject = objectMapper.readValue(messageString, MessageModel.class);

        assertEquals(messageModel, messageObject);
    }
}