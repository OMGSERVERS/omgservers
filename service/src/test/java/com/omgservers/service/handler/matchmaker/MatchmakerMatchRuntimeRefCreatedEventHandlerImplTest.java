package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerMatchRuntimeRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchRuntimeRefCreatedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerMatchRuntimeRefCreatedEventHandlerImplTestInterface matchmakerMatchRuntimeRefCreatedEventHandler;

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
        final var matchmakerMatch = testDataFactory.getMatchmakerTestDataFactory().createMatchmakerMatch(matchmaker);
        final var matchRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createMatchRuntime(tenant, version, matchmakerMatch);
        final var matchmakerMatchRuntimeRef = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerMatchRuntimeRef(matchmakerMatch, matchRuntime);

        final var matchmakerId = matchmakerMatchRuntimeRef.getMatchmakerId();
        final var matchId = matchmakerMatchRuntimeRef.getMatchId();
        final var id = matchmakerMatchRuntimeRef.getId();

        final var eventBody = new MatchmakerMatchRuntimeRefCreatedEventBodyModel(matchmakerId, matchId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchRuntimeRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchRuntimeRefCreatedEventHandler.handle(eventModel);
    }
}