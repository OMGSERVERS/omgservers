package com.omgservers.service.handler.matchmaker;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchRuntimeRefDeletedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerMatchRuntimeRefDeletedEventHandlerImpl matchRuntimeRefDeletedEventHandler;

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

        final var matchmakerId = matchmakerMatchRuntimeRef.getMatchmakerId();
        final var matchId = matchmakerMatchRuntimeRef.getMatchId();
        final var id = matchmakerMatchRuntimeRef.getId();

        final var deleteMatchmakerMatchRuntimeRefRequest = new DeleteMatchmakerMatchRuntimeRefRequest(matchmakerId,
                matchId,
                id);
        matchmakerService.deleteMatchmakerMatchRuntimeRef(deleteMatchmakerMatchRuntimeRefRequest);

        final var eventBody = new MatchmakerMatchRuntimeRefDeletedEventBodyModel(matchmakerId, matchId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchRuntimeRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchRuntimeRefDeletedEventHandler.handle(eventModel);
    }
}