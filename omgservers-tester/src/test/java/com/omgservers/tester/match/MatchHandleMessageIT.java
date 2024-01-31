package com.omgservers.tester.match;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
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

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@QuarkusTest
public class MatchHandleMessageIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void matchHandleMessageIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""                       
                        """,
                """
                        local var command = ...
                                            
                        if command.qualifier == "handle_message" then
                            local var message = command.message
                            assert(message.text == "helloworld", "message.text is wrong")
                            return {
                                {
                                    qualifier = "respond",
                                    client_id = command.client_id,
                                    message = {
                                        text = "match_message_was_handled"
                                    }
                                }
                            }
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }})
        );

        Thread.sleep(10_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.WELCOME_MESSAGE);

            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));

            playerApiTester.requestMatchmaking(testClient, "test");

            final var matchAssignment = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));

            playerApiTester.sendMessage(testClient, new TestMessage("helloworld"));

            final var serverMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_MESSAGE,
                    Collections.singletonList(matchAssignment.getId()));
            assertEquals("{text=match_message_was_handled}",
                    ((ServerMessageBodyModel) serverMessage.getBody()).getMessage().toString());

            Thread.sleep(10_000);
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
            Thread.sleep(10_000);
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
