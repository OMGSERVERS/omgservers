package com.omgservers.tester.test;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.operation.BootstrapTestVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@ApplicationScoped
public class BootstrapVersionTest extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    public void testBootstrapVersion() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion("""
                print("test")
                """);

        try {
            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
            Thread.sleep(10000);
        }
    }
}
