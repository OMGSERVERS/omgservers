package com.omgservers.service.handler;

import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.handler.testOperation.CreateSimpleDataOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
@QuarkusTest
class ClientCreatedEventHandlerImplTest extends Assertions {

    @Inject
    ClientCreatedEventHandlerImpl clientCreatedEventHandler;

    @Inject
    CreateSimpleDataOperation createTestVersion;

    @Inject
    EventModelFactory eventModelFactory;

    @Test
    void givenTestClient_whenHandleClientCreatedEventEventAgain_thenOk() {
        final var simpleData = createTestVersion.createSimpleData();

        final var eventBody = new ClientCreatedEventBodyModel(
                simpleData.testClientHolder().client().getUserId(),
                simpleData.testClientHolder().client().getId());

        final var eventModel = eventModelFactory.create(eventBody);
        clientCreatedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
        clientCreatedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
    }
}