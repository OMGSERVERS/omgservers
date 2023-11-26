package com.omgservers.tester;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class MatchMessagingIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void matchMessagingIT() throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        """,
                """
                        local var command = ...
                                                
                        if command.qualifier == "handle_message" then
                            return {
                                {
                                    qualifier = "unicast",
                                    user_id = command.user_id,
                                    client_id = command.client_id,
                                    message = {
                                        text = "received"
                                    }
                                }
                            }
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }}));

        Thread.sleep(10000);

        try {

            final var client1 = testClientFactory.create();
            client1.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);

            client1.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);

            client1.sendMatchMessage(new TestMessage("request"));

            final var event1 = client1.consumeServerMessage();
            assertEquals("{text=received}", event1.getMessage().toString());

            client1.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }

    @Data
    @AllArgsConstructor
    class TestMessage {
        String text;
    }
}
