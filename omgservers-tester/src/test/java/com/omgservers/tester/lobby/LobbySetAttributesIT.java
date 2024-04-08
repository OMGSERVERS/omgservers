package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbySetAttributesIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbySetAttributesIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        function handle_command(self, command)

                            if command.qualifier == "init_runtime" then
                                self.attributes = {}
                            end

                            if command.qualifier == "add_client" then
                                self.attributes[command.client_id] = command.attributes
                            end

                            if command.qualifier == "handle_message" then
                                local var text = command.message.text
                                
                                if text == "init_attributes" then
                                    return {
                                        {
                                            qualifier = "set_attributes",
                                            client_id = command.client_id,
                                            attributes = {
                                                a1 = 1,
                                                a2 = "string",
                                                a3 = 3.14,
                                                a4 = true
                                            }
                                        },
                                        {
                                            qualifier = "respond_client",
                                            client_id = command.client_id,
                                            message = {
                                                text = "attributes_was_init"
                                            }
                                        }
                                    }
                                elseif text == "check_attributes" then
                                    local attributes = self.attributes[command.client_id]
                                    assert(type(attributes.a1) == "number", "a1 is wrong")
                                    assert(type(attributes.a2) == "string", "a2 is wrong")
                                    assert(type(attributes.a3) == "number", "a3 is wrong")
                                    assert(type(attributes.a4) == "boolean", "a4 is wrong")
                                    
                                    return {
                                        {
                                            qualifier = "respond_client",
                                            client_id = command.client_id,
                                            message = {
                                                text = "attributes_was_checked"
                                            }
                                        }
                                    }
                                end
                                
                            end
                        end
                        """,
                """
                        function handle_command(self, command)
                        end
                        """);

        Thread.sleep(10000);

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));

            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage("init_attributes"));

            final var serverMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));
            assertEquals("{text=attributes_was_init}",
                    ((ServerOutgoingMessageBodyModel) serverMessage1.getBody()).getMessage().toString());

            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion, testClient1);

            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                    Collections.singletonList(serverMessage1.getId()));

            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));

            final var matchmakerAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));

            playerApiTester.sendMessage(testClient2, new TestMessage("check_attributes"));

            final var serverMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));
            assertEquals("{text=attributes_was_checked}",
                    ((ServerOutgoingMessageBodyModel) serverMessage2.getBody()).getMessage().toString());

        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
