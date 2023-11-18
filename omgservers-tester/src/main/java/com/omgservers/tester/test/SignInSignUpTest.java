package com.omgservers.tester.test;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.BootstrapTestVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

@Slf4j
@ApplicationScoped
public class SignInSignUpTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    public void testSignInSignUp(final URI gatewayUri) throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion("""
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


        try {

            final var client = testClientFactory.create(gatewayUri);

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
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
