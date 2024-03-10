package com.omgservers.tester.player;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerCreateClientIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void createClientIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""                       
                        function handle_command(self, command)
                        end
                        """,
                """
                        function handle_command(self, command)
                        end
                        """);

        Thread.sleep(10000);

        try {
            final var testClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            assertNotNull(testClient.getUserId());
            assertNotNull(testClient.getPassword());
            assertNotNull(testClient.getRawToken());
            assertNotNull(testClient.getClientId());

        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
