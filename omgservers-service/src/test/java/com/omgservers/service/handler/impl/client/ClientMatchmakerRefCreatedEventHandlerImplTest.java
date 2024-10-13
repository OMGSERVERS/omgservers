package com.omgservers.service.handler.impl.client;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.client.testInterface.ClientMatchmakerRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientMatchmakerRefCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientMatchmakerRefCreatedEventHandlerImplTestInterface clientMatchmakerRefCreatedEventHandler;

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
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmaker(tenant, tenantDeployment);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);
        final var clientMatchmakerRef = testDataFactory.getClientTestDataFactory()
                .createClientMatchmakerRef(client, matchmaker);

        final var clientId = client.getId();
        final var id = clientMatchmakerRef.getId();

        final var eventBody = new ClientMatchmakerRefCreatedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientMatchmakerRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientMatchmakerRefCreatedEventHandler.handle(eventModel);
    }
}