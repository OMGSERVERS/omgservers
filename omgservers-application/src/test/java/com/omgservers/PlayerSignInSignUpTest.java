package com.omgservers;

import com.omgservers.utils.operation.BootstrapVersionOperation;
import com.omgservers.utils.testClient.TestClientFactory;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

@Slf4j
@QuarkusTest
public class PlayerSignInSignUpTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void playerSignInSignUpTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                function signed_up(self, event, player)
                    player.respond("player_signed_up")
                end
                                
                function signed_in(self, event, player)
                    player.respond("player_signed_in")
                end
                                
                print("version was initialized")
                """);

        final var client = testClientFactory.create(uri);
        client.signUp(version);
        var event1 = client.consumeEventMessage();
        assertEquals("player_signed_up", event1.getEvent().toString());

        client.reconnect();
        client.signIn(version);
        var event2 = client.consumeEventMessage();
        assertEquals("player_signed_in", event2.getEvent().toString());
        client.close();
    }
}
