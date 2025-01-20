package com.omgservers.tester.match;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyDto;
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
public class MatchInactiveRuntimeDetectionIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void matchInactiveRuntimeDetectionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation
                .bootstrapTestVersion("""
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
                                            if command_qualifier == "HANDLE_MESSAGE" then
                                                local var message = command_body.message
                                                if message.text == "trigger_exit" then
                                                    os.exit()    
                                                end
                                            end
                                        end
                                    end,
                                })
                                """,
                        new TenantVersionConfigDto(new ArrayList<>() {{
                            add(TenantVersionModeDto.create("test", 1, 16, new ArrayList<>() {{
                                add(new TenantVersionGroupDto("players", 1, 16));
                            }}));
                        }}, null));

        try {
            final var testClient1 = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage1.getId()));
            final var lobbyRuntimeId1 = ((RuntimeAssignmentMessageBodyDto) lobbyAssignment1.getBody())
                    .getRuntimeId();
            final var matchmakerAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment1.getId()));

            playerApiTester.sendMessage(testClient1, new TestMessage("request_matchmaking"));
            final var matchAssignment1 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment1.getId()));

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

            final var lobbyAssignment2 = playerApiTester.waitMessage(testClient1,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchAssignment1.getId()));
            final var lobbyRuntimeId2 = ((RuntimeAssignmentMessageBodyDto) lobbyAssignment2.getBody())
                    .getRuntimeId();

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
