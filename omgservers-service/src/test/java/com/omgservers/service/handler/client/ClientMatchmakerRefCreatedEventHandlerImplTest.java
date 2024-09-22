package com.omgservers.service.handler.client;

import com.omgservers.service.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientMatchmakerRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientMatchmakerRefCreatedEventHandlerImplTest extends Assertions {

    @Inject
    ClientMatchmakerRefCreatedEventHandlerImplTestInterface clientMatchmakerRefCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, version);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);
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