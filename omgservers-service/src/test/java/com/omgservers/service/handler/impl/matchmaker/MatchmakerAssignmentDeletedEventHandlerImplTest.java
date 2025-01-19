package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerAssignmentDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerAssignmentDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerAssignmentDeletedEventHandlerImplTestInterface matchmakerAssignmentDeletedEventHandler;

    @Inject
    MatchmakerServiceTestInterface matchmakerService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var matchmakerId = testData.getMatchmaker().getId();
        final var id = testData.getMatchmakerAssignment().getId();

        final var deleteMatchmakerAssignmentRequest = new DeleteMatchmakerAssignmentRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerAssignmentRequest);

        final var eventBody = new MatchmakerAssignmentDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerAssignmentDeletedEventHandler.handle(eventModel);
    }
}