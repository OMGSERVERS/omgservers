package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerMatchAssignmentCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchAssignmentCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchAssignmentCreatedEventHandlerImplTestInterface matchmakerMatchAssignmentCreatedEventHandler;

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
        final var matchmakerMatch = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerMatch(matchmaker);
        final var matchRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createMatchRuntime(tenant, tenantDeployment, matchmakerMatch);
        final var matchmakerMatchRuntimeRef = testDataFactory
                .getMatchmakerTestDataFactory()
                .createMatchmakerMatchRuntimeRef(matchmakerMatch, matchRuntime);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);
        final var matchmakerAssignment = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerAssignment(matchmaker, client);
        final var matchmakerMatchAssignment = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerMatchAssignment(matchmakerMatch, client);

        final var matchmakerId = matchmakerMatchAssignment.getMatchmakerId();
        final var matchId = matchmakerMatchAssignment.getMatchId();
        final var id = matchmakerMatchAssignment.getId();

        final var eventBody = new MatchmakerMatchAssignmentCreatedEventBodyModel(matchmakerId, matchId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchAssignmentCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchAssignmentCreatedEventHandler.handle(eventModel);
    }
}