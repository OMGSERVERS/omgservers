//package com.omgservers.tester.match;
//
//import com.omgservers.schema.message.MessageQualifierEnum;
//import com.omgservers.schema.message.body.ConnectionUpgradedMessageBodyDto;
//import com.omgservers.schema.message.body.ConnectionUpgradeQualifierEnum;
//import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
//import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
//import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
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
//import java.util.ArrayList;
//import java.util.Collections;
//
//@Slf4j
//@QuarkusTest
//public class MatchUpgradeConnectionIT extends BaseTestClass {
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
//    void matchUpgradeConnectionIT() throws Exception {
//        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
//                        local omgserver = require("omgserver")
//                        omgserver:enter_loop({
//                            handle = function(self, command_qualifier, command_body)
//                                local runtime_qualifier = omgserver.qualifier
//
//                                if runtime_qualifier == "LOBBY" then
//                                    if command_qualifier == "HANDLE_MESSAGE" then
//                                        local var text = command_body.message.text
//                                        if text == "request_matchmaking" then
//                                            return {
//                                                {
//                                                    qualifier = "REQUEST_MATCHMAKING",
//                                                    body = {
//                                                        client_id = command_body.client_id,
//                                                        mode = "test"
//                                                    }
//                                                }
//                                            }
//                                        end
//                                    end
//                                elseif runtime_qualifier == "MATCH" then
//                                    if command_qualifier == "ADD_MATCH_CLIENT" then
//                                        return {
//                                            {
//                                                qualifier = "UPGRADE_CONNECTION",
//                                                body = {
//                                                    client_id = command_body.client_id,
//                                                    protocol = "DISPATCHER",
//                                                }
//                                            }
//                                        }
//                                    end
//                                end
//                            end,
//                        })
//                        """,
//                new TenantVersionConfigDto(new ArrayList<>() {{
//                    add(TenantVersionModeDto.create("test", 1, 16, new ArrayList<>() {{
//                        add(new TenantVersionGroupDto("players", 1, 16));
//                    }}));
//                }}, null)
//        );
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
//            playerApiTester.sendMessage(testClient, new TestMessage("request_matchmaking"));
//
//            final var matchAssignment = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
//                    Collections.singletonList(matchmakerAssignment.getId()));
//
//            final var connectionUpgrade = playerApiTester.waitMessage(testClient,
//                    MessageQualifierEnum.CONNECTION_UPGRADE_MESSAGE,
//                    Collections.singletonList(matchAssignment.getId()));
//
//            assertEquals(testClient.getClientId(),
//                    ((ConnectionUpgradedMessageBodyDto) connectionUpgrade.getBody()).getClientId());
//            assertEquals(ConnectionUpgradeQualifierEnum.DISPATCHER,
//                    ((ConnectionUpgradedMessageBodyDto) connectionUpgrade.getBody()).getProtocol());
//            assertNotNull(((ConnectionUpgradedMessageBodyDto) connectionUpgrade.getBody()).getWebSocketConfig());
//            assertNotNull(((ConnectionUpgradedMessageBodyDto) connectionUpgrade.getBody()).getWebSocketConfig()
//                    .getWsToken());
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
