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
public class PlayerSetGetObjectTest extends Assertions {

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
                local var attributes = context.attributes
                local var object = context.object
                local var event = context.event
                                
                print("event: " .. event.id)

                if event.id == "signed_up" then
                    print(event.client_id)
                    context.set_object({
                        a1 = 1,
                        a2 = "string",
                        a3 = 3.14,
                        a4 = true
                    })
                end
                                
                if event.id == "signed_in" then
                    print(event.client_id)
                    local var object = context.get_object()
                    assert(type(object.a1) == "number", "a1 is wrong")
                    assert(type(object.a2) == "string", "a2 is wrong")
                    assert(type(object.a3) == "number", "a3 is wrong")
                    assert(type(object.a4) == "boolean", "a4 is wrong")
                    context.respond({text="test passed"})
                end
                """);

        final var client = testClientFactory.create(uri);
        client.signUp(version);

        final var welcome1 = client.consumeWelcomeMessage();
        assertNotNull(welcome1);

        client.reconnect();
        client.signIn(version);

        var message1 = client.consumeServerMessage();
        assertEquals("{text=test passed}", message1.getMessage().toString());

        final var welcome2 = client.consumeWelcomeMessage();
        assertNotNull(welcome2);

        client.close();
    }
}
