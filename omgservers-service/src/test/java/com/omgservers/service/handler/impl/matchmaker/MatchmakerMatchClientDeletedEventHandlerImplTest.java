package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerMatchClientDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchClientDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchClientDeletedEventHandlerImplTestInterface matchmakerMatchClientDeletedEventHandler;

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
        final var matchmakerMatchClient = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerMatchClient(matchmakerMatch, client);
        final var matchRuntimeAssignment = testDataFactory.getRuntimeTestDataFactory()
                .createRuntimeAssignment(matchRuntime, client);

        final var matchmakerId = matchmakerMatchClient.getMatchmakerId();
        final var matchId = matchmakerMatchClient.getMatchId();
        final var id = matchmakerMatchClient.getId();

        final var deleteMatchmakerMatchClientRequest = new DeleteMatchmakerMatchClientRequest(matchmakerId, id);
        matchmakerService.deleteMatchmakerMatchClient(deleteMatchmakerMatchClientRequest);

        final var eventBody = new MatchmakerMatchClientDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchClientDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchClientDeletedEventHandler.handle(eventModel);
    }
}