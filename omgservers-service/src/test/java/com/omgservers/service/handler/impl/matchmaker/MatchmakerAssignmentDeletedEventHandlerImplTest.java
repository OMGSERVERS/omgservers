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
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var matchmaker = testDataFactory.getMatchmakerTestDataFactory().createMatchmaker(tenant, tenantDeployment);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, tenantDeployment);
        final var matchmakerAssignment = testDataFactory.getMatchmakerTestDataFactory()
                .createMatchmakerAssignment(matchmaker, client);
        final var clientMatchmakerRef = testDataFactory.getClientTestDataFactory()
                .createClientMatchmakerRef(client, matchmaker);

        final var matchmakerId = matchmakerAssignment.getMatchmakerId();
        final var id = matchmakerAssignment.getId();

        final var deleteMatchmakerAssignmentRequest = new DeleteMatchmakerAssignmentRequest(matchmakerId, id);
        matchmakerService.execute(deleteMatchmakerAssignmentRequest);

        final var eventBody = new MatchmakerAssignmentDeletedEventBodyModel(matchmakerId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerAssignmentDeletedEventHandler.handle(eventModel);
    }
}