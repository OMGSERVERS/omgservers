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
public class PlayerMatchmakingTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void playerMatchmakingTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""                        
                        local var state = context.state
                        local var event = context.event
                                                                        
                        print("event: " .. event.id)

                        if event.id == "signed_up" then
                            context.respond("signed_up")
                        end
                                                
                        if event.id == "signed_in" then
                            context.respond("signed_in")
                        end
                                                
                        if event.id == "init" then
                            state.actions = {}
                            table.insert(state.actions, event)
                        end
                                                
                        if event.id == "add_player" then
                            table.insert(state.actions, event)
                        end
                                                
                        if event.id == "handle_message" then
                            table.insert(state.actions, event)
                            
                            context.unicast_message(event.user_id, event.client_id, event.data.text)
                        end
                           
                        if event.id == "update" then
                            table.insert(state.actions, event)
                            
                            -- context.unicast_message(event.user_id, event.client_id, event.data.text)
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

        final var event1 = client1.consumeEventMessage();
        assertEquals("signed_up", event1.getEvent().toString());
        final var event2 = client2.consumeEventMessage();
        assertEquals("signed_up", event2.getEvent().toString());

        log.info("Request matchmaking");

        client1.requestMatchmaking("death-match");
        client2.requestMatchmaking("death-match");

        Thread.sleep(5000);

        client1.sendMatchMessage(new TestMessage("Hello, "));
        final var event3 = client1.consumeEventMessage();
        assertEquals("Hello, ", event3.getEvent().toString());

        client2.sendMatchMessage(new TestMessage("world!"));
        final var event4 = client2.consumeEventMessage();
        assertEquals("world!", event4.getEvent().toString());

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
