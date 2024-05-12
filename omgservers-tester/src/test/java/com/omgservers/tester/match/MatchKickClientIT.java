package com.omgservers.tester.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
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
public class MatchKickClientIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void matchKickClientIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                            elseif qualifier == "MATCH" then
                                if command.qualifier == "HANDLE_MESSAGE" then
                                    local var message = command.message
                                    return {
                                        {
                                            qualifier = "KICK_CLIENT",
                                            body = {
                                                client_id = command.message.client_id
                                            }
                                        }
                                    }
                                end
                                if command.qualifier == "DELETE_CLIENT" then
                                    return {
                                        {
                                            qualifier = "BROADCAST_MESSAGE",
                                            body =  {
                                                message = {
                                                    text = "client_was_deleted"
                                                }
                                            }
                                        }
                                    }
                                end
                            end
                        end)
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }})
        );

        Thread.sleep(16_000);

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            // Welcome messages

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            // Lobby assignments

            final var lobbyAssignment11 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var lobbyAssignment21 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));

            // Matchmaker assignments

            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment11.getId()));
            final var matchmakerAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment21.getId()));

            // Matchmaking requests

            playerApiTester.requestMatchmaking(testClient1, "test");
            playerApiTester.requestMatchmaking(testClient2, "test");

            // Match assignments

            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));
            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage(testClient2.getClientId()));

            // Match message for client1

            final var serverMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchAssignment1.getId()));
            assertEquals("{text=client_was_deleted}",
                    ((ServerOutgoingMessageBodyModel) serverMessage1.getBody()).getMessage().toString());

            // Lobby assignment for client2

            final var lobbyAssignment22 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchAssignment2.getId()));

        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        @JsonProperty("client_id")
        Long id;
    }
}
