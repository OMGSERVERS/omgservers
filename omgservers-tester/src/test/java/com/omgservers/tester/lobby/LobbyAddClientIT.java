package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
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
public class LobbyAddClientIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void lobbyAddClientIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        function handle_command(self, command)
                            if command.qualifier == "add_client" then
                                return {
                                    {
                                        qualifier = "respond_client",
                                        client_id = command.client_id,
                                        message = {
                                            text = "client_was_added"
                                        }
                                    }
                                }
                            end
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
            final var serverMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));

            assertEquals("{text=client_was_added}",
                    ((ServerMessageBodyModel) serverMessage.getBody()).getMessage().toString());
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
