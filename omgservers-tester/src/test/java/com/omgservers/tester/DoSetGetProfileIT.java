package com.omgservers.tester;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DoSetGetProfileIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void doSetGetProfileIT() throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion("""
                local var command = ...
                                
                if command.qualifier == "sign_up" then
                    return {
                        {
                            qualifier = "set_profile",
                            user_id = command.user_id,
                            client_id = command.client_id,
                            profile = {
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
                    local var profile = command.profile
                    assert(type(profile.a1) == "number", "a1 is wrong")
                    assert(type(profile.a2) == "string", "a2 is wrong")
                    assert(type(profile.a3) == "number", "a3 is wrong")
                    assert(type(profile.a4) == "boolean", "a4 is wrong")
                    
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

        try {
            final var client = testClientFactory.create();
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
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
