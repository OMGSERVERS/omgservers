package com.omgservers.tester.lobby;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.schema.model.version.VersionGroupModel;
import com.omgservers.schema.model.version.VersionModeModel;
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

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbyMultipleReassignmentIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbyMultipleReassignmentIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local omgserver = require("omgserver")
                        omgserver:enter_loop({
                            handle = function(self, command_qualifier, command_body)
                                local runtime_qualifier = omgserver.qualifier
                                
                                if runtime_qualifier == "LOBBY" then
                                    if command_qualifier == "HANDLE_MESSAGE" then
                                        local var text = command_body.message.text
                                        if text == "request_matchmaking" then
                                            return {
                                                {
                                                    qualifier = "REQUEST_MATCHMAKING",
                                                    body = {
                                                        client_id = command_body.client_id,
                                                        mode = "test"
                                                    }
                                                }
                                            }
                                        end
                                    end
                                elseif runtime_qualifier == "MATCH" then
                                    if command_qualifier == "HANDLE_MESSAGE" then
                                        local var message = command_body.message
                                        return {
                                            {
                                                qualifier = "KICK_CLIENT",
                                                body = {
                                                    client_id = command_body.client_id
                                                }
                                            }
                                        }
                                    end
                                end
                            end,
                        })                        
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }})
        );

        Thread.sleep(32_000);

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

            playerApiTester.sendMessage(testClient, new TestMessage("request_matchmaking"));

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

            playerApiTester.sendMessage(testClient, new TestMessage("request_matchmaking"));

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

            Thread.sleep(32_000);

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
