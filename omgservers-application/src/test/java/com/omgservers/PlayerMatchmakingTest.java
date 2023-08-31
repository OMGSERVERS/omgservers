package com.omgservers;

import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.utils.operation.BootstrapVersionOperation;
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
                        function player_signed_up(event, player)
                            player.respond("signed_up")
                        end

                        function player_signed_in(event, player)
                            player.respond("signed_in")
                        end
                                                
                        function runtime_update(event, runtime)
                            print(event.step)
                        end

                        print("version was initialized")
                        """,
                new VersionStageConfigModel(new ArrayList<>() {{
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

        client1.close();
        client2.close();
    }
}