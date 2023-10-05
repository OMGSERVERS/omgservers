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
                local var state = context.state
                local var event = context.event
                                
                if event.id == "signed_up" then
                    context.respond({text="signed_up"})
                end
                                
                if event.id == "signed_in" then
                    context.respond({text="signed_in"})
                end
                                
                return nil
                """);

        final var client = testClientFactory.create(uri);
        client.signUp(version);

        final var welcome1 = client.consumeWelcomeMessage();
        assertNotNull(welcome1);

        final var serverMessage1 = client.consumeServerMessage();
        assertEquals("{text=signed_up}", serverMessage1.getMessage().toString());

        client.reconnect();
        client.signIn(version);

        final var welcome2 = client.consumeWelcomeMessage();
        assertNotNull(welcome2);

        final var serverMessage2 = client.consumeServerMessage();
        assertEquals("{text=signed_in}", serverMessage2.getMessage().toString());
        client.close();
    }
}
