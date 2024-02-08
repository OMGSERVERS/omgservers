package com.omgservers.tester.player;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerWelcomeMessageIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void welcomeMessageIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""                       
                        """,
                """
                        """);

        Thread.sleep(10_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.WELCOME_MESSAGE);

            assertNotNull(welcomeMessage);
            final var messageBody = ((WelcomeMessageBodyModel) welcomeMessage.getBody());
            assertNotNull(messageBody.getTenantId());
            assertNotNull(messageBody.getVersionId());

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
            Thread.sleep(10_000);
        }
    }
}