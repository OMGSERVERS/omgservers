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
public class LobbyHandleMessageIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbyHandleMessage() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                                if command.qualifier == "HANDLE_MESSAGE" then
                                    local var message = command.message
                                    assert(message.text == "helloworld", "message.text is wrong")
                                    return {
                                        {
                                            qualifier = "RESPOND_CLIENT",
                                            body = {
                                                client_id = command.client_id,
                                                message = {
                                                    text = "message_was_handled"
                                                }
                                            }
                                        }
                                    }
                                end
                            elseif qualifier == "MATCH" then
                            end
                        end)
                        """);

        Thread.sleep(32_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            final var matchmakerAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));

            playerApiTester.sendMessage(testClient, new TestMessage("helloworld"));

            final var serverMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment.getId()));
            assertEquals("{text=message_was_handled}",
                    ((ServerOutgoingMessageBodyModel) serverMessage.getBody()).getMessage().toString());

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
