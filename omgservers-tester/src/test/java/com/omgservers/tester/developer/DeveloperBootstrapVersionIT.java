package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperBootstrapVersionIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    public void bootstrapVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        function handle_command(self, command)
                        end
                        """,
                """
                        function handle_command(self, command)
                        end
                        """);

        try {
            Thread.sleep(10000);
        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}