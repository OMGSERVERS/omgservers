package com.omgservers;

import com.omgservers.utils.AdminCli;
import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
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
public class ChangePlayerIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Inject
    AdminCli adminCli;

    @Test
    void changePlayerTest() throws Exception {
        final var version =
                bootstrapVersionOperation.bootstrapVersion("""                                               
                        local var command = ...

                        if command.qualifier == "change_player" then
                            local var message = command.message
                            assert(message.text == "reset", "message.text is wrong")
                            return {
                                {
                                    qualifier = "respond",
                                    user_id = command.user_id,
                                    client_id = command.client_id,
                                    message = {
                                        text = "changed"
                                    }
                                }
                            }
                        end
                        """);

        Thread.sleep(10000);

        try {
            final var client = testClientFactory.create(uri);

            client.signUp(version);
            final var welcome1 = client.consumeWelcomeMessage();
            assertNotNull(welcome1);

            client.changeRequest(new TestMessage("reset"));

            final var serverMessage1 = client.consumeServerMessage();
            assertEquals("{text=changed}", serverMessage1.getMessage().toString());

            client.close();

            Thread.sleep(10000);
        } finally {
            adminCli.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }

    @Data
    @AllArgsConstructor
    class TestMessage {
        String text;
    }
}
