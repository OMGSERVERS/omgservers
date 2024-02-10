package com.omgservers.tester.match;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MatchKickClientIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void matchKickClientIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""                       
                        """,
                """
                        function handle_command(self, command)
                                                                                     
                            if command.qualifier == "handle_message" then
                                local var message = command.message
                                
                                return {
                                    {
                                        qualifier = "kick",
                                        client_id = command.message.client_id
                                    }
                                }
                            end
                                                
                            if command.qualifier == "delete_client" then
                                return {
                                    {
                                        qualifier = "broadcast",
                                        message = {
                                            text = "client_was_deleted"
                                        }
                                    }
                                }
                            end
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("test", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }})
        );

        Thread.sleep(10_000);

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.WELCOME_MESSAGE);
            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.WELCOME_MESSAGE);

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));

            playerApiTester.requestMatchmaking(testClient1, "test");
            playerApiTester.requestMatchmaking(testClient2, "test");

            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));
            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage(testClient2.getClientId()));

            final var serverMessage = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_MESSAGE,
                    Collections.singletonList(matchAssignment1.getId()));
            assertEquals("{text=client_was_deleted}",
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
        @JsonProperty("client_id")
        Long id;
    }
}
