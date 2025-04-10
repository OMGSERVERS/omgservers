//package com.omgservers.tester.lobby;
//
//import com.omgservers.schema.message.MessageQualifierEnum;
//import com.omgservers.schema.message.body.MessageProducedMessageBodyDto;
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.PlayerApiTester;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
//import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collections;
//
//@Slf4j
//@QuarkusTest
//public class LobbyHandleMessageIT extends BaseTestClass {
//
//    @Inject
//    BootstrapTestVersionOperation bootstrapTestVersionOperation;
//
//    @Inject
//    BootstrapTestClientOperation bootstrapTestClientOperation;
//
//    @Inject
//    PlayerApiTester playerApiTester;
//
//    @Inject
//    SupportApiTester supportApiTester;
//
//    @Test
//    void lobbyHandleMessage() throws Exception {
//        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
//                """
//                        local omgserver = require("omgserver")
//                        omgserver:enter_loop({
//                            handle = function(self, command_qualifier, command_body)
//                                local runtime_qualifier = omgserver.qualifier
//
//                                if runtime_qualifier == "LOBBY" then
//                                    if command_qualifier == "HANDLE_MESSAGE" then
//                                        local var message = command_body.message
//                                        assert(message.text == "helloworld", "message.text is wrong")
//                                        return {
//                                            {
//                                                qualifier = "RESPOND_CLIENT",
//                                                body = {
//                                                    client_id = command_body.client_id,
//                                                    message = {
//                                                        text = "message_was_handled"
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    end
//                                elseif runtime_qualifier == "MATCH" then
//                                end
//                            end,
//                        })
//                        """);
//
//        try {
//            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);
//
//            final var welcomeMessage = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
//
//            final var lobbyAssignment = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
//                    Collections.singletonList(welcomeMessage.getId()));
//
//            final var matchmakerAssignment = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
//                    Collections.singletonList(lobbyAssignment.getId()));
//
//            playerApiTester.sendMessage(testClient, new TestMessage("helloworld"));
//
//            final var serverMessage = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
//                    Collections.singletonList(matchmakerAssignment.getId()));
//            assertEquals("{text=message_was_handled}",
//                    ((MessageProducedMessageBodyDto) serverMessage.getBody()).getMessage().toString());
//
//        } finally {
//            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
//        }
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class TestMessage {
//        String text;
//    }
//}
