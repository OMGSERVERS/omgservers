package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerMatchCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    MatchmakerMatchCreatedEventHandlerImplTestInterface matchmakerMatchCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, tenantDeployment);
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