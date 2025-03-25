package com.omgservers.service.handler.client;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientRuntimeRefDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.client.service.testInterface.ClientServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientRuntimeRefDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientRuntimeRefDeletedEventHandlerImplTestInterface clientRuntimeRefDeletedEventHandler;

    @Inject
    ClientServiceTestInterface clientService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();
        final var clientRuntimeRef = testData.getClientRuntimeRef();

        final var clientId = clientRuntimeRef.getClientId();
        final var id = clientRuntimeRef.getId();

        final var deleteClientRuntimeRefRequest = new DeleteClientRuntimeRefRequest(clientId, id);
        clientService.deleteClientRuntimeRef(deleteClientRuntimeRefRequest);

        final var eventBody = new ClientRuntimeRefDeletedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientRuntimeRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        clientRuntimeRefDeletedEventHandler.handle(eventModel);
    }
}