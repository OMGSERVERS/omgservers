package com.omgservers.tester.player;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerWelcomeMessageBodyModel;
import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerWelcomeMessageIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    PlayerApiTester playerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void welcomeMessageIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""                       
                        require("omgservers").enter_loop(function(self, qualifier, command)
                            if qualifier == "LOBBY" then
                            elseif qualifier == "MATCH" then
                            end
                        end)
                        """);

        Thread.sleep(32_000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(testClient,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);

            assertNotNull(welcomeMessage);
            final var messageBody = ((ServerWelcomeMessageBodyModel) welcomeMessage.getBody());
            assertNotNull(messageBody.getTenantId());
            assertNotNull(messageBody.getVersionId());

        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
