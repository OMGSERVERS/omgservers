package com.omgservers.tester.test;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.net.URI;

@Slf4j
@ApplicationScoped
public class ChangePlayerTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Inject
    AdminApiTester adminApiTester;

    public void testChangePlayer(URI gatewayUri) throws Exception {
        final var version = bootstrapTestVersionOperation
                .bootstrapTestVersion("""                                               
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
            final var client = testClientFactory.create(gatewayUri);

            client.signUp(version);
            final var welcome1 = client.consumeWelcomeMessage();
            assertNotNull(welcome1);

            client.changeRequest(new TestMessage("reset"));

            final var serverMessage1 = client.consumeServerMessage();
            assertEquals("{text=changed}", serverMessage1.getMessage().toString());

            client.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }

    @Data
    @AllArgsConstructor
    class TestMessage {
        String text;
    }
}
