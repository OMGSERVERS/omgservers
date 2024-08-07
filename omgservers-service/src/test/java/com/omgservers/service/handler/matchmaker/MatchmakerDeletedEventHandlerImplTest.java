package com.omgservers.service.handler.matchmaker;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.matchmaker.testInterfaces.MatchmakerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerDeletedEventHandlerImplTest extends Assertions {

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
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, version);

        final var matchmakerId = matchmaker.getId();

        final var deleteMatchmakerRequest = new DeleteMatchmakerRequest(matchmakerId);
        matchmakerService.deleteMatchmaker(deleteMatchmakerRequest);

        final var eventBody = new MatchmakerDeletedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerDeletedEventHandler.handle(eventModel);
    }
}