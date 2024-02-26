package com.omgservers.tester.developer;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import com.omgservers.tester.operation.uploadTestVersion.UploadTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperUploadNewVersionIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    UploadTestVersionOperation uploadTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void uploadNewVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        """,
                """
                        """);

        Thread.sleep(30_000);

        try {
            uploadTestVersionOperation.uploadTestVersion(testVersion, """                                               
                            """,
                    """
                            """);

            Thread.sleep(120_000);
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
        }
    }
}
