package com.omgservers.tester.test;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
public class DoKickClientTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    public void testDoKickClient(final URI gatewayUri) throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local var command = ...
                                               
                        if command.qualifier == "add_client" then
                            local var user_id = command.user_id
                            local var client_id = command.client_id
                            
                            if admin then
                                return {
                                    {
                                        qualifier = "kick",
                                        user_id = command.user_id,
                                        client_id = command.client_id
                                    }
                                }
                            else
                                admin = {
                                    user_id = command.user_id,
                                    client_id = command.client_id
                                }
                            end
                        end
                                                
                        if command.qualifier == "delete_client" then
                            return {
                                {
                                    qualifier = "broadcast",
                                    message = {
                                        text = "kicked"
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


            final var client1 = testClientFactory.create(gatewayUri);
            client1.signUp(version);
            final var client2 = testClientFactory.create(gatewayUri);
            client2.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);
            final var welcome2 = client2.consumeWelcomeMessage();
            assertNotNull(welcome2);

            client1.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);

            client2.requestMatchmaking("death-match");

            final var assignment2 = client2.consumeAssignmentMessage();
            assertNotNull(assignment2);

            final var event1 = client2.consumeRevocationMessage();
            assertNotNull(event1);

            final var event2 = client1.consumeServerMessage();
            assertEquals("{text=kicked}", event2.getMessage().toString());

            client1.close();
            client2.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
