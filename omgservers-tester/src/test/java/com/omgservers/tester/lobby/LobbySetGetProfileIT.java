package com.omgservers.tester.lobby;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
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

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbySetGetProfileIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void lobbySetGetProfileIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        function handle_command(self, command)

                            if command.qualifier == "handle_message" then
                                local var text = command.message.text
                                
                                if text == "init_profile" then
                                    return {
                                        {
                                            qualifier = "set_profile",
                                            client_id = command.client_id,
                                            profile = {
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
                                                text = "profile_was_init"
                                            }
                                        }
                                    }
                                elseif text == "check_profile" then
                                    local var profile = command.profile
                                    assert(type(profile.a1) == "number", "a1 is wrong")
                                    assert(type(profile.a2) == "string", "a2 is wrong")
                                    assert(type(profile.a3) == "number", "a3 is wrong")
                                    assert(type(profile.a4) == "boolean", "a4 is wrong")
                                    
                                    return {
                                        {
                                            qualifier = "respond_client",
                                            client_id = command.client_id,
                                            message = {
                                                text = "profile_was_checked"
                                            }
                                        }
                                    }
                                end
                                
                            end
                        end
                        """,
                """
                        """);

        Thread.sleep(10000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.WELCOME_MESSAGE);

            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            playerApiTester.sendMessage(testClient, new TestMessage("init_profile"));

            final var serverMessage1 = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));
            assertEquals("{text=profile_was_init}",
                    ((ServerMessageBodyModel) serverMessage1.getBody()).getMessage().toString());

            playerApiTester.sendMessage(testClient, new TestMessage("check_profile"));

            final var serverMessage2 = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_MESSAGE,
                    Collections.singletonList(serverMessage1.getId()));
            assertEquals("{text=profile_was_checked}",
                    ((ServerMessageBodyModel) serverMessage2.getBody()).getMessage().toString());

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
