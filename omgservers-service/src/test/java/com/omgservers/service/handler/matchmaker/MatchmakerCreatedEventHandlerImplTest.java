package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerCreatedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerCreatedEventHandlerImplTestInterface matchmakerCreatedEventHandler;

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

        final var matchmakerId = matchmaker.getId();

        final var eventBody = new MatchmakerCreatedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerCreatedEventHandler.handle(eventModel);
    }
}