package com.omgservers.service.handler.impl.client;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.client.testInterface.ClientMatchmakerRefDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.client.impl.service.clientService.testInterface.ClientServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientMatchmakerRefDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientMatchmakerRefDeletedEventHandlerImplTestInterface clientMatchmakerRefDeletedEventHandler;

    @Inject
    ClientServiceTestInterface clientService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(stage, version);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant,
                tenantDeployment);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);
        final var clientMatchmakerRef = testDataFactory.getClientTestDataFactory()
                .createClientMatchmakerRef(client, matchmaker);

        final var clientId = client.getId();
        final var id = clientMatchmakerRef.getId();

        final var deleteClientMatchmakerRefRequest = new DeleteClientMatchmakerRefRequest(clientId, id);
        clientService.deleteClientMatchmakerRef(deleteClientMatchmakerRefRequest);

        final var eventBody = new ClientMatchmakerRefDeletedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientMatchmakerRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        clientMatchmakerRefDeletedEventHandler.handle(eventModel);
    }
}