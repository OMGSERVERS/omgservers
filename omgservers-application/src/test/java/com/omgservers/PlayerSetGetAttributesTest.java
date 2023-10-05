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
public class PlayerSetGetAttributesTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void playerSetGetAttributeTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                local var state = context.state
                local var event = context.event
                                
                if event.id == "signed_up" then
                    context.set_attributes({
                        a1 = 1,
                        a2 = "string",
                        a3 = 3.14,
                        a4 = true
                    })
                end
                                
                if event.id == "signed_in" then
                    local var attributes = context.get_attributes()
                    assert(type(attributes.a1) == "number", "a1 is wrong")
                    assert(type(attributes.a2) == "string", "a2 is wrong")
                    assert(type(attributes.a3) == "number", "a3 is wrong")
                    assert(type(attributes.a4) == "boolean", "a4 is wrong")
                    context.respond({text="test passed"})
                end
                """);

        final var client = testClientFactory.create(uri);
        client.signUp(version);

        final var welcome1 = client.consumeWelcomeMessage();
        assertNotNull(welcome1);

        client.reconnect();
        client.signIn(version);

        final var welcome2 = client.consumeWelcomeMessage();
        assertNotNull(welcome2);

        var message1 = client.consumeServerMessage();
        assertEquals("{text=test passed}", message1.getMessage().toString());

        client.close();
    }
}
