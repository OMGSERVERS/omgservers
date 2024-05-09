package com.omgservers.tester.player;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerCreateClientIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void createClientIT() throws Exception {
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

            assertNotNull(testClient.getUserId());
            assertNotNull(testClient.getPassword());
            assertNotNull(testClient.getRawToken());
            assertNotNull(testClient.getClientId());

        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
