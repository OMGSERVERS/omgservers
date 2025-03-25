package com.omgservers.service.handler.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerMatchCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchResourceCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchCreatedEventHandlerImplTestInterface matchmakerMatchCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var matchmakerMatch = testData.getMatchmakerMatchResource();
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var id = matchmakerMatch.getId();

        final var eventBody = new MatchmakerMatchResourceCreatedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchCreatedEventHandler.handle(eventModel);
    }
}