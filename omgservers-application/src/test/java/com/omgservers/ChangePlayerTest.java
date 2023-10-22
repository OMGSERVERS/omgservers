package com.omgservers;

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

@Slf4j
@QuarkusTest
public class ChangePlayerTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void changePlayerTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                local var state = context.state
                local var event = context.event
                                
                if event.id == "change_player" then
                    assert(event.data.text == "text", "event.data.text is wrong")
                    print("event.data.text=" .. event.data.text)
                    context.respond({text="changed"})
                end
                """);

        final var client = testClientFactory.create(uri);
        client.signUp(version);

        final var welcome = client.consumeWelcomeMessage();
        assertNotNull(welcome);

        client.changeRequest(new TestMessage("text"));

        var message = client.consumeServerMessage();
//        assertEquals("{text=changed}", message.getMessage().toString());

        client.close();
    }

    @Data
    @AllArgsConstructor
    class TestMessage {
        String text;
    }
}
