package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import com.omgservers.tester.operation.createTestVersion.CreateTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperCreateNewVersionIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void createNewVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        end)
                        require("omgservers").enter_loop(function(self, command)
                        """);

        Thread.sleep(16_000);

        try {
            createTestVersionOperation.createTestVersion(testVersion,
                    """
                            require("omgservers").enter_loop(function(self, command)
                            end)
                            """);

            Thread.sleep(120_000);
        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }
}
