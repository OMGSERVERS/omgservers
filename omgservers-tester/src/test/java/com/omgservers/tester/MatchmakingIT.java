package com.omgservers.tester;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class MatchmakingIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void matchmakingIT() throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        """,
                """
                        local var command = ...
                                                
                        if command.qualifier == "add_client" then
                                                
                            return {
                                {
                                    qualifier = "unicast",
                                    user_id = command.user_id,
                                    client_id = command.client_id,
                                    message = {
                                        text = "added"
                                    }
                                }
                            }
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }}));

        Thread.sleep(10000);

        try {

            final var client1 = testClientFactory.create();
            client1.signUp(version);
            final var client2 = testClientFactory.create();
            client2.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);
            final var welcome2 = client2.consumeWelcomeMessage();
            assertNotNull(welcome2);

            client1.requestMatchmaking("death-match");
            client2.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);
            final var assignment2 = client2.consumeAssignmentMessage();
            assertNotNull(assignment2);

            final var event12 = client1.consumeServerMessage();
            assertEquals("{text=added}", event12.getMessage().toString());

            final var event21 = client2.consumeServerMessage();
            assertEquals("{text=added}", event21.getMessage().toString());

            client1.close();
            client2.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
