package com.omgservers.tester.matchmaking;

import com.omgservers.model.message.MessageQualifierEnum;
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
public class MatchmakingInstantRequestsIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void matchmakingInstantRequestsIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local omgserver = require("omgserver")
                        omgserver:enter_loop({
                            handle = function(self, command_qualifier, command_body)
                                local runtime_qualifier = omgserver.qualifier
                                
                                if runtime_qualifier == "LOBBY" then
                                    if command_qualifier == "HANDLE_MESSAGE" then
                                        local var text = command_qualifier.message.text
                                        if text == "request_matchmaking" then
                                            return {
                                                {
                                                    qualifier = "REQUEST_MATCHMAKING",
                                                    body = {
                                                        client_id = command_qualifier.client_id,
                                                        mode = "test"
                                                    }
                                                }
                                            }
                                        end
                                    end
                                elseif runtime_qualifier == "MATCH" then
                                end
                            end,
                        })
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 5, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 5, 16));
                    }}));
                }})
        );

        Thread.sleep(16_000);

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient3 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient4 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient5 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            // Welcome messages

            final var welcomeMessage1 =
                    playerApiTester.waitMessage(testClient1, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var welcomeMessage2 =
                    playerApiTester.waitMessage(testClient2, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var welcomeMessage3 =
                    playerApiTester.waitMessage(testClient3, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var welcomeMessage4 =
                    playerApiTester.waitMessage(testClient4, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var welcomeMessage5 =
                    playerApiTester.waitMessage(testClient5, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            // Lobby assignments

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));
            final var lobbyAssignment3 = playerApiTester.waitMessage(testClient3,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage3.getId()));
            final var lobbyAssignment4 = playerApiTester.waitMessage(testClient4,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage4.getId()));
            final var lobbyAssignment5 = playerApiTester.waitMessage(testClient5,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage5.getId()));

            // Matchmaker assignments

            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));
            final var matchmakerAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));
            final var matchmakerAssignment3 = playerApiTester.waitMessage(testClient3,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment3.getId()));
            final var matchmakerAssignment4 = playerApiTester.waitMessage(testClient4,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment4.getId()));
            final var matchmakerAssignment5 = playerApiTester.waitMessage(testClient5,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment5.getId()));

            // Instant matchmaking requests

            playerApiTester.sendMessage(testClient1, new TestMessage("request_matchmaking"));
            playerApiTester.sendMessage(testClient2, new TestMessage("request_matchmaking"));
            playerApiTester.sendMessage(testClient3, new TestMessage("request_matchmaking"));
            playerApiTester.sendMessage(testClient4, new TestMessage("request_matchmaking"));
            playerApiTester.sendMessage(testClient5, new TestMessage("request_matchmaking"));

            // Match assignments

            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));
            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));
            final var matchAssignment3 = playerApiTester.waitMessage(testClient3,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment3.getId()));
            final var matchAssignment4 = playerApiTester.waitMessage(testClient4,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment4.getId()));
            final var matchAssignment5 = playerApiTester.waitMessage(testClient5,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment5.getId()));

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
