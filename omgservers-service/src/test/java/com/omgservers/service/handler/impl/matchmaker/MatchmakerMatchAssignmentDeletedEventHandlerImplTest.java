package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerMatchAssignmentDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchAssignmentDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchAssignmentDeletedEventHandlerImplTestInterface matchmakerMatchAssignmentDeletedEventHandler;

    @Inject
    MatchmakerServiceTestInterface matchmakerService;

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
        final var matchRuntimeAssignment = testDataFactory.getRuntimeTestDataFactory()
                .createRuntimeAssignment(matchRuntime, client);

        final var matchmakerId = matchmakerMatchAssignment.getMatchmakerId();
        final var matchId = matchmakerMatchAssignment.getMatchId();
        final var id = matchmakerMatchAssignment.getId();

        final var deleteMatchmakerMatchAssignmentRequest = new DeleteMatchmakerMatchAssignmentRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerMatchAssignmentRequest);

        final var eventBody = new MatchmakerMatchAssignmentDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchAssignmentDeletedEventHandler.handle(eventModel);
    }
}