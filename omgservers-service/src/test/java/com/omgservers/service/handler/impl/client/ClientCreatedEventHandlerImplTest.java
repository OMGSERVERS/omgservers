package com.omgservers.service.handler.impl.client;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.client.testInterface.ClientCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientCreatedEventHandlerImplTestInterface clientCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory().createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory().createTenantVersion(tenantProject);
        final var tenantDeployment =
                testDataFactory.getTenantTestDataFactory().createTenantDeployment(tenantStage, tenantVersion);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, tenantDeployment);

        final var eventBody = new ClientCreatedEventBodyModel(client.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        clientCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientCreatedEventHandler.handle(eventModel);
    }
}