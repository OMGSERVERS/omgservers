package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import com.omgservers.tester.operation.createTestVersion.CreateTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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
                        function handle_command(self, command)
                        end
                        """,
                """
                        function handle_command(self, command)
                        end
                        """);

        Thread.sleep(10_000);

        try {
            createTestVersionOperation.createTestVersion(testVersion, """                                               
                            """,
                    """
                            """);

            Thread.sleep(120_000);
        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
