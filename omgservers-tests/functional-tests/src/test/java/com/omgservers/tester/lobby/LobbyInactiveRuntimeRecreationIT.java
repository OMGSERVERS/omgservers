package com.omgservers.tester.lobby;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyModel;
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
public class LobbyInactiveRuntimeRecreationIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbyInactiveRuntimeRecreationIT() throws Exception {
        final var testVersion =
                bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local omgserver = require("omgserver")
                        omgserver:enter_loop({
                            handle = function(self, command_qualifier, command_body)
                                local runtime_qualifier = omgserver.qualifier
                                
                                if runtime_qualifier == "LOBBY" then
                                    if command_qualifier == "HANDLE_MESSAGE" then
                                        local var message = command_body.message
                                        if message.text == "trigger_exit" then
                                            os.exit()    
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
            final var lobbyRuntimeId1 = ((RuntimeAssignmentMessageBodyModel) lobbyAssignment1.getBody())
                    .getRuntimeId();
            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage("trigger_exit"));

            log.info("Waiting for runtime inactivity detection");
            Thread.sleep(10_000);
            playerApiTester.sendMessage(testClient1, new TestMessage("prevent_client_inactivity"));
            Thread.sleep(10_000);
            playerApiTester.sendMessage(testClient1, new TestMessage("prevent_client_inactivity"));
            Thread.sleep(10_000);
            playerApiTester.sendMessage(testClient1, new TestMessage("prevent_client_inactivity"));
            Thread.sleep(10_000);
            playerApiTester.sendMessage(testClient1, new TestMessage("prevent_client_inactivity"));

            final var disconnectionMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.DISCONNECTION_REASON_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));
            assertEquals(DisconnectionReasonEnum.INTERNAL_FAILURE,
                    ((DisconnectionReasonMessageBodyModel) disconnectionMessage1.getBody()).getReason());

            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));

            final var lobbyRuntimeId2 = ((RuntimeAssignmentMessageBodyModel) lobbyAssignment2.getBody())
                    .getRuntimeId();
            assertNotEquals(lobbyRuntimeId2, lobbyRuntimeId1);

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
