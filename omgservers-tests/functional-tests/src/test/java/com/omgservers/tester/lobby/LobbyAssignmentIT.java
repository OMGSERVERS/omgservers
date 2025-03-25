//package com.omgservers.tester.lobby;
//
//import com.omgservers.schema.message.MessageQualifierEnum;
//import com.omgservers.schema.message.body.RuntimeAssignedMessageBodyDto;
//import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.PlayerApiTester;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
//import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collections;
//
//@Slf4j
//@QuarkusTest
//public class LobbyAssignmentIT extends BaseTestClass {
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
//    void lobbyAssignmentIT() throws Exception {
//        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
//                local omgserver = require("omgserver")
//                omgserver:enter_loop({
//                    handle = function(self, command_qualifier, command_body)
//                        local runtime_qualifier = omgserver.qualifier
//
//                        if runtime_qualifier == "LOBBY" then
//                        elseif runtime_qualifier == "MATCH" then
//                        end
//                    end,
//                })
//                """);
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
//            final var messageBody = ((RuntimeAssignedMessageBodyDto) lobbyAssignment.getBody());
//            assertNotNull(messageBody.getRuntimeId());
//            assertEquals(RuntimeQualifierEnum.LOBBY, messageBody.getRuntimeQualifier());
//            assertNotNull(messageBody.getRuntimeConfig());
//
//            final var matchmakerAssignment = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
//                    Collections.singletonList(lobbyAssignment.getId()));
//
//        } finally {
//            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
//        }
//    }
//}
