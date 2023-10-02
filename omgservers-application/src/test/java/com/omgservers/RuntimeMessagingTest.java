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
public class RuntimeMessagingTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void runtimeMessagingTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""                        
                        local var state = context.state
                        local var event = context.event

                        print("event: " .. event.id)
                                                
                        if event.id == "init_runtime" then
                            state.step = 0
                            state.actions = {}
                            table.insert(state.actions, event)
                            
                            state.clients = {}
                        end
                                                
                        if event.id == "add_client" then
                            table.insert(state.clients, {
                                user_id = event.user_id,
                                player_id = event.player_id,
                                client_id = event.client_id
                            })
                            table.insert(state.actions, event)
                        end
                           
                        if event.id == "update_runtime" then
                            state.step = state.step + 1
                            table.insert(state.actions, event)
                            
                            local client_1 = state.clients[1]
                            local client_2 = state.clients[2]
                            
                            context.unicast_message(client_1.user_id, client_1.client_id, "hello, client")
                            context.unicast_message(client_2.user_id, client_2.client_id, "hello, client")
                            
                            local recipients = {
                                {
                                    user_id = client_1.user_id,
                                    client_id = client_1.client_id
                                },
                                {
                                    user_id = client_2.user_id,
                                    client_id = client_2.client_id
                                }
                            }
                            context.multicast_message(recipients, "hello, client_1 and client_2")
                            
                            context.broadcast_message("hello, all")
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

        final var event11 = client1.consumeServerMessage();
        assertEquals("hello, client", event11.getEvent().toString());

        final var event21 = client2.consumeServerMessage();
        assertEquals("hello, client", event21.getEvent().toString());

        final var event12 = client1.consumeServerMessage();
        assertEquals("hello, client_1 and client_2", event12.getEvent().toString());

        final var event22 = client2.consumeServerMessage();
        assertEquals("hello, client_1 and client_2", event22.getEvent().toString());

        final var event13 = client1.consumeServerMessage();
        assertEquals("hello, all", event13.getEvent().toString());

        final var event23 = client2.consumeServerMessage();
        assertEquals("hello, all", event23.getEvent().toString());

        Thread.sleep(5000);

        client1.close();
        client2.close();
    }

    @Data
    @AllArgsConstructor
    class TestMessage {
        String text;
    }
}
