package com.omgservers.service.handler.client;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.client.client.DeleteClientRequest;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.client.service.testInterface.ClientServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientDeletedEventHandlerImplTestInterface clientDeletedEventHandler;

    @Inject
    ClientServiceTestInterface clientService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();
        final var client = testData.getClient();

        final var deleteClientRequest = new DeleteClientRequest(client.getId());
        clientService.deleteClient(deleteClientRequest);

        final var eventBody = new ClientDeletedEventBodyModel(client.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        clientDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        clientDeletedEventHandler.handle(eventModel);
    }
}