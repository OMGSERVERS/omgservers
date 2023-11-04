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
public class SetGetObjectIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void setGetAttributeTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                local var command = ...
                
                if command.qualifier == "sign_up" then
                    return {
                        {
                            qualifier = "set_object",
                            user_id = command.user_id,
                            client_id = command.client_id,
                            object = {
                                a1 = 1,
                                a2 = "string",
                                a3 = 3.14,
                                a4 = true
                            }
                        },
                        {
                            qualifier = "respond",
                            user_id = command.user_id,
                            client_id = command.client_id,
                            message = {
                                text = "signed_up"
                            }
                        }
                    }
                end
                                
                if command.qualifier == "sign_in" then
                    local var object = command.object
                    assert(type(object.a1) == "number", "a1 is wrong")
                    assert(type(object.a2) == "string", "a2 is wrong")
                    assert(type(object.a3) == "number", "a3 is wrong")
                    assert(type(object.a4) == "boolean", "a4 is wrong")
                    
                    return {
                        {
                            qualifier = "respond",
                            user_id = command.user_id,
                            client_id = command.client_id,
                            message = {
                                text = "signed_in"
                            }
                        }
                    }
                end
                """);

        Thread.sleep(10000);

        final var client = testClientFactory.create(uri);
        client.signUp(version);
        final var welcome1 = client.consumeWelcomeMessage();
        assertNotNull(welcome1);
        final var message1 = client.consumeServerMessage();
        assertEquals("{text=signed_up}", message1.getMessage().toString());

        client.reconnect();
        client.signIn(version);
        final var welcome2 = client.consumeWelcomeMessage();
        assertNotNull(welcome2);
        var message = client.consumeServerMessage();
        assertEquals("{text=signed_in}", message.getMessage().toString());

        client.close();

        Thread.sleep(10000);
    }
}
