package com.omgservers.service.handler.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.matchmaker.service.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerDeletedEventHandlerImplTestInterface matchmakerDeletedEventHandler;

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

        final var deleteMatchmakerRequest = new DeleteMatchmakerRequest(matchmakerId);
        matchmakerService.execute(deleteMatchmakerRequest);

        final var eventBody = new MatchmakerDeletedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerDeletedEventHandler.handle(eventModel);
    }
}