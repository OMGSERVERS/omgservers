package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbyMultipleReassignmentIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void lobbyMultipleReassignmentIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        function handle_command(self, command)
                        end
                        """,
                """
                        function handle_command(self, command)
                            if command.qualifier == "handle_message" then
                                local var message = command.message
                                return {
                                    {
                                        qualifier = "kick_client",
                                        client_id = command.client_id
                                    }
                                }
                            end
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }})
        );

        Thread.sleep(10_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));

            // Reassignment 1

            playerApiTester.requestMatchmaking(testClient, "test");

            final var matchAssignment1 = playerApiTester.waitMessage(testClient,
                    message -> message.getQualifier().equals(MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE) &&
                            ((RuntimeAssignmentMessageBodyModel) message.getBody()).getRuntimeQualifier().equals(
                                    RuntimeQualifierEnum.MATCH),
                    Collections.singletonList(matchmakerAssignment1.getId()));

            playerApiTester.sendMessage(testClient, new TestMessage("kick_me"));

            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient,
                    message -> message.getQualifier().equals(MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE) &&
                            ((RuntimeAssignmentMessageBodyModel) message.getBody()).getRuntimeQualifier().equals(
                                    RuntimeQualifierEnum.LOBBY),
                    Collections.singletonList(matchAssignment1.getId()));

            // Reassignment 2

            playerApiTester.requestMatchmaking(testClient, "test");

            final var matchAssignment2 = playerApiTester.waitMessage(testClient,
                    message -> message.getQualifier().equals(MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE) &&
                            ((RuntimeAssignmentMessageBodyModel) message.getBody()).getRuntimeQualifier().equals(
                                    RuntimeQualifierEnum.MATCH),
                    Collections.singletonList(lobbyAssignment2.getId()));

            playerApiTester.sendMessage(testClient, new TestMessage("kick_me"));

            final var lobbyAssignment3 = playerApiTester.waitMessage(testClient,
                    message -> message.getQualifier().equals(MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE) &&
                            ((RuntimeAssignmentMessageBodyModel) message.getBody()).getRuntimeQualifier().equals(
                                    RuntimeQualifierEnum.LOBBY),
                    Collections.singletonList(matchAssignment2.getId()));

            Thread.sleep(10_000);

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
