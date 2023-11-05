package com.omgservers;

import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.utils.operation.deleteVersionOperation.DeleteVersionOperation;
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
public class SignInSignUpIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    DeleteVersionOperation deleteVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void signInSignUpTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                local var command = ...
                                
                if command.qualifier == "sign_up" then
                    return {
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
        final var serverMessage1 = client.consumeServerMessage();
        assertEquals("{text=signed_up}", serverMessage1.getMessage().toString());

        client.reconnect();
        client.signIn(version);
        final var welcome2 = client.consumeWelcomeMessage();
        assertNotNull(welcome2);
        final var serverMessage2 = client.consumeServerMessage();
        assertEquals("{text=signed_in}", serverMessage2.getMessage().toString());
        client.close();

        Thread.sleep(10000);

        deleteVersionOperation.deleteVersion(version);
        Thread.sleep(10000);
    }
}
