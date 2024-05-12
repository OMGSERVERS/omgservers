package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.DisconnectionReasonEnum;
import com.omgservers.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbyInactiveClientDetectionIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbyInactiveClientDetectionIT() throws Exception {
        final var testVersion =
                bootstrapTestVersionOperation.bootstrapTestVersion("""
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                            elseif qualifier == "MATCH" then
                            end
                        end)
                        """);

        Thread.sleep(16_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));
            final var matchmakerAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));

            log.info("Waiting for disconnection");
            Thread.sleep(30_000);

            final var disconnectionMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.DISCONNECTION_REASON_MESSAGE,
                    Collections.singletonList(matchmakerAssignment.getId()));

            assertEquals(DisconnectionReasonEnum.CLIENT_INACTIVITY,
                    ((DisconnectionReasonMessageBodyModel) disconnectionMessage.getBody()).getReason());

        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
