package com.omgservers.service.handler;

import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.handler.testOperation.CreateSimpleDataOperation;
import com.omgservers.service.module.user.operation.testInterface.DeleteClientOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
@QuarkusTest
class ClientDeletedEventHandlerImplTest extends Assertions {

    @Inject
    ClientDeletedEventHandlerImpl clientDeletedEventHandler;

    @Inject
    CreateSimpleDataOperation createTestVersion;

    @Inject
    DeleteClientOperationTestInterface deleteClientOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Test
    void givenTestClient_whenHandleClientCreatedEventEventAgain_thenOk() {
        final var shard = 0;

        final var simpleData = createTestVersion.createSimpleData();

        deleteClientOperation.deleteClient(shard,
                simpleData.testClientHolder().client().getUserId(),
                simpleData.testClientHolder().client().getId());

        final var eventBody = new ClientDeletedEventBodyModel(
                simpleData.testClientHolder().client().getUserId(),
                simpleData.testClientHolder().client().getId());

        final var eventModel = eventModelFactory.create(eventBody);
        clientDeletedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
        clientDeletedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
    }
}