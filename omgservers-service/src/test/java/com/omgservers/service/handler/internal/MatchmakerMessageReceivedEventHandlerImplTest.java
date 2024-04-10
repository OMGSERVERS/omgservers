package com.omgservers.service.handler.internal;

import com.omgservers.model.event.body.internal.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.MatchmakerMessageReceivedEventHandlerImplTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class MatchmakerMessageReceivedEventHandlerImplTest extends Assertions {

    @Inject
    MatchmakerMessageReceivedEventHandlerImplTestInterface matchmakerMessageReceivedEventHandler;

    @Inject
    GenerateIdOperation generateIdOperation;

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
        final var versionMatchmakerRef = testDataFactory.getTenantTestDataFactory()
                .createVersionMatchmakerRef(version, matchmaker);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);
        final var clientMatchmakerRef = testDataFactory.getClientTestDataFactory()
                .createClientMatchmakerRef(client, matchmaker);

        final var clientId = client.getId();
        final var message = new MessageModel(generateIdOperation.generateId(),
                MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                new MatchmakerMessageBodyModel("mode"));

        final var eventBody = new MatchmakerMessageReceivedEventBodyModel(clientId, message);
        final var eventModel = eventModelFactory.create(eventBody);

        matchmakerMessageReceivedEventHandler.handle(eventModel);
        log.info("Retry");
        matchmakerMessageReceivedEventHandler.handle(eventModel);
    }
}