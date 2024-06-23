package com.omgservers.tester.matchmaking;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.RuntimeAssignmentMessageBodyModel;
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
public class MatchmakingNewMatchAssignmentIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void matchmakingNewMatchAssignmentIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                                if command.qualifier == "HANDLE_MESSAGE" then
                                    local var text = command.message.text
                                    if text == "request_matchmaking" then
                                        return {
                                            {
                                                qualifier = "REQUEST_MATCHMAKING",
                                                body = {
                                                    client_id = command.client_id,
                                                    mode = "test"
                                                }
                                            }
                                        }
                                    end
                                end
                            elseif qualifier == "MATCH" then
                            end
                        end)
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 1, 1, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 1));
                    }}));
                }})
        );

        Thread.sleep(16_000);

        try {
            // Client 1

            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var welcomeMessage1 =
                    playerApiTester.waitMessage(testClient1, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));
            playerApiTester.sendMessage(testClient1, new TestMessage("request_matchmaking"));
            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));

            // Client 2

            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var welcomeMessage2 =
                    playerApiTester.waitMessage(testClient2, MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));
            final var matchmakerAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));
            playerApiTester.sendMessage(testClient2, new TestMessage("request_matchmaking"));
            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));

            // Joined a new match

            assertNotEquals(
                    ((RuntimeAssignmentMessageBodyModel) matchAssignment1.getBody()).getRuntimeId(),
                    ((RuntimeAssignmentMessageBodyModel) matchAssignment2.getBody()).getRuntimeId()
            );

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
