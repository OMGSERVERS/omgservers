package com.omgservers.service.handler.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerCreatedEventHandlerImplTestInterface matchmakerCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();
        final var matchmaker = testData.getMatchmaker();

        final var matchmakerId = matchmaker.getId();

        final var eventBody = new MatchmakerCreatedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerCreatedEventHandler.handle(eventModel);
    }
}