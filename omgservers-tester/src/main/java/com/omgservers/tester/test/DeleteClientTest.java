package com.omgservers.tester.test;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.BootstrapTestVersionOperation;
import io.quarkus.test.common.http.TestHTTPResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
public class DeleteClientTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    public void testDeleteClient(final URI gatewayUri) throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion("""
                        local var command = ...

                        if command.qualifier == "init_runtime" then
                            clients = {}
                        end
                                               
                        if command.qualifier == "add_client" then
                            clients[command.client_id] = {
                                user_id = command.user_id,
                                client_id = command.client_id
                            }
                        end
                                                
                        if command.qualifier == "delete_client" then
                            local var client = clients[command.client_id]
                            assert(client.user_id ~= nil, "client.user_id is wrong")
                            assert(client.client_id ~= nil, "client.client_id is wrong")
                            
                            return {
                                {
                                    qualifier = "broadcast",
                                    message = {
                                        text = "deleted"
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
            final var client1 = testClientFactory.create(gatewayUri);
            client1.signUp(version);
            final var client2 = testClientFactory.create(gatewayUri);
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

            Thread.sleep(5000);

            client1.close();

            final var event = client2.consumeServerMessage();
            assertEquals("{text=deleted}", event.getMessage().toString());
            client2.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
