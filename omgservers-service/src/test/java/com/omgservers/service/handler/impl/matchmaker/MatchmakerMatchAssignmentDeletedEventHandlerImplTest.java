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
        final var testData = testDataFactory.createDefaultTestData();

        final var matchmakerId = testData.getMatchmakerMatchAssignment().getMatchmakerId();
        final var matchId = testData.getMatchmakerMatchAssignment().getMatchId();
        final var id = testData.getMatchmakerMatchAssignment().getId();

        final var deleteMatchmakerMatchAssignmentRequest = new DeleteMatchmakerMatchAssignmentRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerMatchAssignmentRequest);

        final var eventBody = new MatchmakerMatchAssignmentDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchAssignmentDeletedEventHandler.handle(eventModel);
    }
}