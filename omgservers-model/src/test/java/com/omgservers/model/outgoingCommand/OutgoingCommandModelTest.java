package com.omgservers.model.outgoingCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.outgoingCommand.body.RespondClientOutgoingCommandBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
@QuarkusTest
class OutgoingCommandModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenOutgoingCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var qualifier = OutgoingCommandQualifierEnum.RESPOND_CLIENT;
        final var clientId = 2000L;
        final var message = "helloworld";
        final var body = new RespondClientOutgoingCommandBodyModel(clientId, message);

        final var outgoingCommandModel = new OutgoingCommandModel(qualifier,
                body);
        log.info("Outgoing command model, {}", outgoingCommandModel);

        final var outgoingCommandString = objectMapper.writeValueAsString(outgoingCommandModel);
        log.info("Deserialized value, {}", outgoingCommandString);

        final var outgoingCommandObject = objectMapper.readValue(outgoingCommandString, OutgoingCommandModel.class);

        assertEquals(outgoingCommandModel, outgoingCommandObject);
    }

}