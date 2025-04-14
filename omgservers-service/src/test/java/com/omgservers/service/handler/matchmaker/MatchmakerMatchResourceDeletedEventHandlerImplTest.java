package com.omgservers.service.handler.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerMatchDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.matchmaker.service.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchResourceDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchDeletedEventHandlerImplTestInterface matchmakerMatchDeletedEventHandler;

    @Inject
    MatchmakerServiceTestInterface matchmakerService;

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

        final var deleteMatchmakerMatchRequest = new DeleteMatchmakerMatchResourceRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerMatchRequest);

        final var eventBody = new MatchmakerMatchResourceDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchDeletedEventHandler.handle(eventModel);
    }
}