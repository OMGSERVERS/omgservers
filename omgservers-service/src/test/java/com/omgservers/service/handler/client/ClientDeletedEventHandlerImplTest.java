package com.omgservers.service.handler.client;

import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.client.impl.service.clientService.testInterface.ClientServiceTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientDeletedEventHandlerImplTest extends Assertions {

    @Inject
    ClientDeletedEventHandlerImplTestInterface clientDeletedEventHandler;

    @Inject
    ClientServiceTestInterface clientService;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    ClientModelFactory clientModelFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());

        final var syncClientRequest = new SyncClientRequest(client);
        clientService.syncClient(syncClientRequest);

        final var deleteClientRequest = new DeleteClientRequest(client.getId());
        clientService.deleteClient(deleteClientRequest);

        final var eventBody = new ClientDeletedEventBodyModel(client.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        clientDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        clientDeletedEventHandler.handle(eventModel);
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long playerId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}