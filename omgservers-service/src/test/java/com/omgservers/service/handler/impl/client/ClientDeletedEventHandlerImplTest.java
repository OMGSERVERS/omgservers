package com.omgservers.service.handler.impl.client;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.client.testInterface.ClientDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.client.impl.service.clientService.testInterface.ClientServiceTestInterface;
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
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory()
                .createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory().createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(tenantProject);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(tenantStage, tenantVersion);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var deleteClientRequest = new DeleteClientRequest(client.getId());
        clientService.deleteClient(deleteClientRequest);

        final var eventBody = new ClientDeletedEventBodyModel(client.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        clientDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        clientDeletedEventHandler.handle(eventModel);
    }
}