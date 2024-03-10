package com.omgservers.tester.matchmaking;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@QuarkusTest
public class MatchmakingGradualRequestsIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void matchmakingGradualRequests() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        function handle_command(self, command)
                        end
                        """,
                """
                        function handle_command(self, command)
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 5, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 5, 16));
                    }}));
                }})
        );

        Thread.sleep(10_000);

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient3 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient4 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient5 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            // Welcome messages

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1, MessageQualifierEnum.WELCOME_MESSAGE);
            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2, MessageQualifierEnum.WELCOME_MESSAGE);
            final var welcomeMessage3 = playerApiTester.waitMessage(testClient3, MessageQualifierEnum.WELCOME_MESSAGE);
            final var welcomeMessage4 = playerApiTester.waitMessage(testClient4, MessageQualifierEnum.WELCOME_MESSAGE);
            final var welcomeMessage5 = playerApiTester.waitMessage(testClient5, MessageQualifierEnum.WELCOME_MESSAGE);

            // Lobby assignments

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));
            final var lobbyAssignment3 = playerApiTester.waitMessage(testClient3,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage3.getId()));
            final var lobbyAssignment4 = playerApiTester.waitMessage(testClient4,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage4.getId()));
            final var lobbyAssignment5 = playerApiTester.waitMessage(testClient5,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage5.getId()));

            // Gradual matchmaking requests

            playerApiTester.requestMatchmaking(testClient1, "test");
            Thread.sleep(1_000);

            playerApiTester.requestMatchmaking(testClient2, "test");
            Thread.sleep(1_000);

            playerApiTester.requestMatchmaking(testClient3, "test");
            Thread.sleep(1_000);

            playerApiTester.requestMatchmaking(testClient4, "test");
            Thread.sleep(1_000);

            playerApiTester.requestMatchmaking(testClient5, "test");
            Thread.sleep(1_000);

            // Match assignments

            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));
            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));
            final var matchAssignment3 = playerApiTester.waitMessage(testClient3,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment3.getId()));
            final var matchAssignment4 = playerApiTester.waitMessage(testClient4,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment4.getId()));
            final var matchAssignment5 = playerApiTester.waitMessage(testClient5,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment5.getId()));

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
