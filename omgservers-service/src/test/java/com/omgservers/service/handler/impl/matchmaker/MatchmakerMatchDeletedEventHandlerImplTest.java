package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.matchmaker.testInterfaces.MatchmakerMatchDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMatchDeletedEventHandlerImplTest extends BaseTestClass {

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
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(stage, version);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant,
                tenantDeployment);
        final var matchmakerMatch = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerMatch(matchmaker);
        final var matchRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createMatchRuntime(tenant, tenantDeployment, matchmakerMatch);

        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var id = matchmakerMatch.getId();

        final var deleteMatchmakerMatchRequest = new DeleteMatchmakerMatchRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerMatchRequest);

        final var eventBody = new MatchmakerMatchDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMatchDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMatchDeletedEventHandler.handle(eventModel);
    }
}