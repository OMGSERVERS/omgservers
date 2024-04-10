package com.omgservers.service.handler.internal;

import com.omgservers.model.event.body.internal.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ClientOutgoingMessageBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.ClientMessageReceivedEventHandlerImplTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientMessageReceivedEventHandlerImplTest extends Assertions {

    @Inject
    ClientMessageReceivedEventHandlerImplTestInterface clientMessageReceivedEventHandler;

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
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);
        final var clientRuntimeRef = testDataFactory.getClientTestDataFactory()
                .createClientRuntimeRef(client, lobbyRuntime);

        final var clientId = client.getId();
        final var message = new MessageModel(generateIdOperation.generateId(),
                MessageQualifierEnum.CLIENT_OUTGOING_MESSAGE,
                new ClientOutgoingMessageBodyModel(new Object()));

        final var eventBody = new ClientMessageReceivedEventBodyModel(clientId, message);
        final var eventModel = eventModelFactory.create(eventBody);

        clientMessageReceivedEventHandler.handle(eventModel);
        log.info("Retry");
        clientMessageReceivedEventHandler.handle(eventModel);
    }
}