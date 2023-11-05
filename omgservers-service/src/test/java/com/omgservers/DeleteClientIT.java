package com.omgservers;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.utils.operation.deleteVersionOperation.DeleteVersionOperation;
import com.omgservers.utils.testClient.TestClientFactory;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class DeleteClientIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    DeleteVersionOperation deleteVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void deleteClientTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
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

        final var client1 = testClientFactory.create(uri);
        client1.signUp(version);
        final var client2 = testClientFactory.create(uri);
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

        deleteVersionOperation.deleteVersion(version);
        Thread.sleep(10000);
    }
}
