package com.omgservers.tester;

import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import com.omgservers.tester.operation.updateTestVersion.UpdateTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class UpdateStageVersionIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    UpdateTestVersionOperation updateTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Test
    void updateStageVersionIT() throws Exception {
        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        print("version1")
                        """,
                """
                        """);

        Thread.sleep(10_000);

        try {
            updateTestVersionOperation.updateTestVersion(testVersion, """                                               
                            print("version2")
                            """,
                    """
                            """);

            Thread.sleep(120_000);
        } finally {
            adminApiTester.deleteTenant(testVersion.getTenantId());
            Thread.sleep(10_000);
        }
    }
}
