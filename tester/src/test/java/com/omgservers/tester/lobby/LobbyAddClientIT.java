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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@QuarkusTest
public class LobbyAddClientIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void lobbyAddClientIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                require("omgservers").enter_loop(function(self, qualifier, command)
                    if qualifier == "LOBBY" then
                        if command.qualifier == "ADD_CLIENT" then
                            return {
                                {
                                    qualifier = "RESPOND_CLIENT",
                                    body = {
                                        client_id = command.client_id,
                                        message = {
                                            text = "client_was_added"
                                        }
                                    }
                                }
                            }
                        end
                    elseif qualifier == "MATCH" then
                    end
                end)
                """);

        Thread.sleep(16_000);

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
            final var serverMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchmakerAssignment.getId()));

            assertEquals("{text=client_was_added}",
                    ((ServerOutgoingMessageBodyModel) serverMessage.getBody()).getMessage().toString());
        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }
}
