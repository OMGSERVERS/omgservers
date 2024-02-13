package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.DisconnectionMessageBodyModel;
import com.omgservers.model.message.body.DisconnectionReasonEnum;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbyInactiveClientDetectionIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void lobbyInactiveClientDetectionIT() throws Exception {
        final var testVersion =
                bootstrapTestVersionOperation.bootstrapTestVersion("""                                              
                                """,
                        """
                                """);

        Thread.sleep(10_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.WELCOME_MESSAGE);
            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            Thread.sleep(60_000);

            final var disconnectionMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.DISCONNECTION_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));

            assertEquals(DisconnectionReasonEnum.CLIENT_INACTIVITY,
                    ((DisconnectionMessageBodyModel) disconnectionMessage.getBody()).getReason());

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
