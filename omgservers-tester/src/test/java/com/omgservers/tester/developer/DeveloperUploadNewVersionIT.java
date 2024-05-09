package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import com.omgservers.tester.operation.uploadTestVersion.UploadTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperUploadNewVersionIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    UploadTestVersionOperation uploadTestVersionOperation;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void uploadNewVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        require("omgservers").enter_loop(function(self, command)
                        end)
                        """);

        Thread.sleep(32_000);

        try {
            uploadTestVersionOperation.uploadTestVersion(testVersion, """                                               
                    """);

            Thread.sleep(120_000);
        } finally {
            supportApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
