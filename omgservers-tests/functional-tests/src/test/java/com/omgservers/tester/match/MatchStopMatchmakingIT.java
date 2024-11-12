package com.omgservers.tester.match;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyDto;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
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

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@QuarkusTest
public class MatchStopMatchmakingIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void matchStopMatchmakingIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local omgserver = require("omgserver")
                        omgserver:enter_loop({
                            handle = function(self, command_qualifier, command_body)
                                local runtime_qualifier = omgserver.qualifier
                                
                                if runtime_qualifier == "LOBBY" then
                                    if command_qualifier == "HANDLE_MESSAGE" then
                                        local var text = command_body.message.text
                                        if text == "request_matchmaking" then
                                            return {
                                                {
                                                    qualifier = "REQUEST_MATCHMAKING",
                                                    body = {
                                                        client_id = command_body.client_id,
                                                        mode = "test"
                                                    }
                                                }
                                            }
                                        end
                                    end
                                elseif runtime_qualifier == "MATCH" then
                                    if command_qualifier == "ADD_MATCH_CLIENT" then
                                        return {
                                            {
                                                qualifier = "STOP_MATCHMAKING",
                                                body = {
                                                }
                                            },
                                            {
                                                qualifier = "RESPOND_CLIENT",
                                                body = {
                                                    client_id = command_body.client_id,
                                                    message = {
                                                        text = "matchmaking_was_stop"
                                                    }
                                                }
                                            }
                                        }
                                    end
                                end
                            end,
                        })                        
                        """,
                new TenantVersionConfigDto(new ArrayList<>() {{
                    add(TenantVersionModeDto.create("test", 1, 16, new ArrayList<>() {{
                        add(new TenantVersionGroupDto("players", 1, 16));
                    }}));
                }}, null)
        );

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));

            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage("request_matchmaking"));

            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));

            final var runtimeId1 = ((RuntimeAssignmentMessageBodyDto) matchAssignment1.getBody()).getRuntimeId();

            final var serverMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchAssignment1.getId()));

            assertEquals("{text=matchmaking_was_stop}",
                    ((ServerOutgoingMessageBodyDto) serverMessage1.getBody()).getMessage().toString());


            final var testClient2 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage2.getId()));

            final var matchmakerAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment2.getId()));

            playerApiTester.sendMessage(testClient2, new TestMessage("request_matchmaking"));

            final var matchAssignment2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment2.getId()));

            final var runtimeId2 = ((RuntimeAssignmentMessageBodyDto) matchAssignment2.getBody()).getRuntimeId();

            final var serverMessage2 = playerApiTester.waitMessage(testClient2,
                    MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                    Collections.singletonList(matchAssignment1.getId()));

            assertEquals("{text=matchmaking_was_stop}",
                    ((ServerOutgoingMessageBodyDto) serverMessage2.getBody()).getMessage().toString());

            assertNotEquals(runtimeId1, runtimeId2);

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
