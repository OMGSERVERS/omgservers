package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
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
public class LobbyAssignmentIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void lobbyAssignmentIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        function handle_command(self, command)
                        end                        
                        """,
                """
                        function handle_command(self, command)
                        end
                        """);

        Thread.sleep(10_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.WELCOME_MESSAGE);

            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            final var messageBody = ((AssignmentMessageBodyModel) lobbyAssignment.getBody());
            assertNotNull(messageBody.getRuntimeId());
            assertEquals(RuntimeQualifierEnum.LOBBY, messageBody.getRuntimeQualifier());
            assertNotNull(messageBody.getRuntimeConfig());

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
