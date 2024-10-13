package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.MatchmakerAssignmentRequestedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerAssignmentRequestedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerAssignmentRequestedEventHandlerImplTestInterface matchmakerAssignmentRequestedEventHandler;

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
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(tenantStage, tenantVersion);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant,
                tenantDeployment);
        final var tenantMatchmakerRef = testDataFactory.getTenantTestDataFactory()
                .createTenantMatchmakerRef(tenantDeployment, matchmaker);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var clientId = client.getId();
        final var tenantId = tenant.getId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var eventBody = new MatchmakerAssignmentRequestedEventBodyModel(clientId, tenantId, tenantDeploymentId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerAssignmentRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerAssignmentRequestedEventHandler.handle(eventModel);
    }
}