package com.omgservers.service.handler.internal;

import com.omgservers.model.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.MatchmakerAssignmentRequestedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerAssignmentRequestedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerAssignmentRequestedEventHandlerImplTestInterface matchmakerAssignmentRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, version);
        final var versionMatchmakerRef = testDataFactory.getTenantTestDataFactory()
                .createVersionMatchmakerRef(version, matchmaker);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);

        final var clientId = client.getId();
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var eventBody = new MatchmakerAssignmentRequestedEventBodyModel(clientId, tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerAssignmentRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerAssignmentRequestedEventHandler.handle(eventModel);
    }
}