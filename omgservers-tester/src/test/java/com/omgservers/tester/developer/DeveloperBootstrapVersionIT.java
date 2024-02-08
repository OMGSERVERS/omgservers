package com.omgservers.tester.developer;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperBootstrapVersionIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    public void bootstrapVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        """,
                """
                        """);

        try {
            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
            Thread.sleep(10000);
        }
    }
}