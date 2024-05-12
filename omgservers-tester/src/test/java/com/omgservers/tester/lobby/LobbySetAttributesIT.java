package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbySetAttributesIT extends BaseTestClass {

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
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                                if command.qualifier == "INIT_RUNTIME" then
                                    self.attributes = {}
                                end
                                if command.qualifier == "ADD_CLIENT" then
                                    self.attributes[command.client_id] = command.attributes
                                end
                                if command.qualifier == "HANDLE_MESSAGE" then
                                    local var text = command.message.text
                                    if text == "init_attributes" then
                                        return {
                                            {
                                                qualifier = "SET_ATTRIBUTES",
                                                body = {
                                                    client_id = command.client_id,
                                                    attributes = {
                                                        attributes = {
                                                            {
                                                                name = "a1",
                                                                type = "LONG",
                                                                value = 1
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                qualifier = "RESPOND_CLIENT",
                                                body =  {
                                                    client_id = command.client_id,
                                                    message = {
                                                        text = "attributes_was_init"
                                                    }
                                                }
                                            }
                                        }
                                    elseif text == "check_attributes" then
                                        local attributes = self.attributes[command.client_id]
                                        return {
                                            {
                                                qualifier = "RESPOND_CLIENT",
                                                body = {
                                                    client_id = command.client_id,
                                                    message = {
                                                        text = "attributes_was_checked"
                                                    }
                                                }
                                            }
                                        }
                                    end
                                end
                            elseif qualifier == "MATCH" then
                            end
                        end)
                        """);

        Thread.sleep(16_000);

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
