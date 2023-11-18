package com.omgservers.tester.test;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.BootstrapTestVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
public class DoStopMatchTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    public void testStopMatch(final URI gatewayUri) throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
                """                                                                                              
                        local var command = ...
                                                
                        if command.qualifier == "add_client" then
                            return {
                                {
                                    qualifier = "stop",
                                    reason = "why not?"
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
            final var client1 = testClientFactory.create(gatewayUri);
            client1.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);

            client1.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);

            client1.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
