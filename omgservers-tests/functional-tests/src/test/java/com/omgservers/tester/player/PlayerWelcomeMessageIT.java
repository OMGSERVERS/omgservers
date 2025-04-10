//package com.omgservers.tester.player;
//
//import com.omgservers.schema.message.MessageQualifierEnum;
//import com.omgservers.schema.message.body.ClientGreetedMessageBodyDto;
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.PlayerApiTester;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//@Slf4j
//@QuarkusTest
//public class PlayerWelcomeMessageIT extends BaseTestClass {
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
//    void welcomeMessageIT() throws Exception {
//        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
//                        local omgserver = require("omgserver")
//                        omgserver:enter_loop({
//                            handle = function(self, command_qualifier, command_body)
//                                local runtime_qualifier = omgserver.qualifier
//
//                                if runtime_qualifier == "LOBBY" then
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
//            assertNotNull(welcomeMessage);
//            final var messageBody = ((ClientGreetedMessageBodyDto) welcomeMessage.getBody());
//            assertNotNull(messageBody.getTenantId());
//            assertNotNull(messageBody.getVersionId());
//
//        } finally {
//            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
//        }
//    }
//}
