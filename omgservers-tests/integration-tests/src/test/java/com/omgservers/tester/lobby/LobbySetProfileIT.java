package com.omgservers.tester.lobby;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyModel;
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
public class LobbySetProfileIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbySetProfileIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        local omgserver = require("omgserver")
                        omgserver:enter_loop({
                            handle = function(self, command_qualifier, command_body)
                                local runtime_qualifier = omgserver.qualifier
                                
                                if runtime_qualifier == "LOBBY" then
                                    if command_qualifier == "INIT_RUNTIME" then
                                        self.profiles = {}
                                    elseif command_qualifier == "ADD_CLIENT" then
                                        self.profiles[command_body.client_id] = command_body.profile
                                    elseif command_qualifier == "HANDLE_MESSAGE" then
                                        local var text = command_body.message.text
                                        if text == "init_profile" then
                                            return {
                                                {
                                                    qualifier = "SET_PROFILE",
                                                    body =  {
                                                        client_id = command_body.client_id,
                                                        profile = {
                                                            a1 = 1,
                                                            a2 = "string",
                                                            a3 = 3.14,
                                                            a4 = true
                                                        }
                                                    }
                                                },
                                                {
                                                    qualifier = "RESPOND_CLIENT",
                                                    body = {
                                                        client_id = command_body.client_id,
                                                        message = {
                                                            text = "profile_was_init"
                                                        }
                                                    }
                                                }
                                            }
                                        elseif text == "check_profile" then
                                            local var profile = self.profiles[command_body.client_id]
                                            assert(type(profile.a1) == "number", "a1 is wrong")
                                            assert(type(profile.a2) == "string", "a2 is wrong")
                                            assert(type(profile.a3) == "number", "a3 is wrong")
                                            assert(type(profile.a4) == "boolean", "a4 is wrong")
                                            return {
                                                {
                                                    qualifier = "RESPOND_CLIENT",
                                                    body =  {
                                                        client_id = command_body.client_id,
                                                        message = {
                                                            text = "profile_was_checked"
                                                        }
                                                    }
                                                }
                                            }
                                        end
                                    end
                                elseif runtime_qualifier == "MATCH" then
                                end
                            end,
                        })                        
                        """);

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

            playerApiTester.sendMessage(testClient1, new TestMessage("init_profile"));

            final var serverMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));
            assertEquals("{text=profile_was_init}",
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

            playerApiTester.sendMessage(testClient2, new TestMessage("check_profile"));

            final var serverMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));
            assertEquals("{text=profile_was_checked}",
                    ((ServerOutgoingMessageBodyModel) serverMessage2.getBody()).getMessage().toString());

        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
