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
                        function signed_up(self, event, player)
                            player.respond("signed_up")
                        end

                        function signed_in(self, event, player)
                            player.respond("signed_in")
                        end

                        function init(self, event, runtime)
                            print("event.id=" .. event.id)
                            self.config = event.config
                            self.actions = {}
                            table.insert(self.actions, event)
                        end
                                          
                        function add_player(self, event, runtime)
                            print("event.id=" .. event.id)
                            print("event.user_id=" .. event.user_id)
                            print("event.player_id=" .. event.player_id)
                            print("event.client_id=" .. event.client_id)
                            print("runtime.matchmaker_id=" .. runtime.matchmaker_id)
                            print("runtime.match_id=" .. runtime.match_id)
                            print("runtime.runtime_id=" .. runtime.runtime_id)
                                                        
                            table.insert(self.actions, event)
                        end
                                                
                        function handle_message(self, event, runtime)                           
                            print("event.id=" .. event.id)
                            print("event.user_id=" .. event.user_id)
                            print("event.player_id=" .. event.player_id)
                            print("event.client_id=" .. event.client_id)
                            print("event.data.text=" .. event.data.text)
                            
                            table.insert(self.actions, event)
                        end
                                                
                        function update(self, event, runtime)
                            print(event.step)
                            print("runtime.matchmaker_id=" .. runtime.matchmaker_id)
                            print("runtime.match_id=" .. runtime.match_id)
                            print("runtime.runtime_id=" .. runtime.runtime_id)
                            
                            table.insert(self.actions, event)
                        end                      

                        print("version was initialized")
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
        client2.sendMatchMessage(new TestMessage("world!"));

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
