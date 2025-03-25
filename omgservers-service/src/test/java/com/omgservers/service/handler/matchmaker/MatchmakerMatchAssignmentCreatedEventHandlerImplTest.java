package com.omgservers.service.handler.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerMatchAssignmentCreatedEventHandlerImplTestInterface;
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
        final var testData = testDataFactory.createDefaultTestData();
        final var matchmakerMatchAssignment = testData.getMatchmakerMatchAssignment();
        final var matchmakerId = matchmakerMatchAssignment.getMatchmakerId();
        final var id = matchmakerMatchAssignment.getId();

        final var eventBody = new MatchmakerMatchAssignmentCreatedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchAssignmentCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchAssignmentCreatedEventHandler.handle(eventModel);
    }
}