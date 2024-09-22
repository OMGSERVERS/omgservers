package com.omgservers.service.handler.matchmaker;

import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerMatchCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchCreatedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerMatchCreatedEventHandlerImplTestInterface matchmakerMatchCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, version);
        final var matchmakerMatch = testDataFactory.getMatchmakerTestDataFactory().createMatchmakerMatch(matchmaker);

        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var id = matchmakerMatch.getId();

        final var eventBody = new MatchmakerMatchCreatedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchCreatedEventHandler.handle(eventModel);
    }
}