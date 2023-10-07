package com.omgservers;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.utils.operation.BootstrapVersionOperation;
import com.omgservers.utils.testClient.TestClientFactory;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class RuntimeDeleteClientTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void runtimeDeleteClientTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""                        
                        local var event = context.event
                        local var state = context.state

                        print("event: " .. event.id)
                                                
                        if event.id == "init_runtime" then                            
                            state.clients = {}
                        end
                                               
                        if event.id == "add_client" then
                            state.clients[event.client_id] = {
                                user_id = event.user_id,
                                player_id = event.player_id
                            }                            
                        end
                                                
                        if event.id == "delete_client" then
                            local var client = state.clients[event.client_id]
                            assert(client.user_id ~= nil, "client.user_id is wrong")
                            assert(client.player_id ~= nil, "client.player_id is wrong")
                            
                            context.broadcast_message({text="client was deleted"})
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }}));

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

        Thread.sleep(5000);

        final var event = client2.consumeServerMessage();
        assertEquals("{text=client was deleted}", event.getMessage().toString());
        client2.close();
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
