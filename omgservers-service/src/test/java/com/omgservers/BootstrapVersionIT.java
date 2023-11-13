package com.omgservers;

import com.omgservers.utils.AdminCli;
import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class BootstrapVersionIT extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    AdminCli adminCli;

    @Test
    void bootstrapVersionTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                print("test")
                """);

        try {
            Thread.sleep(10000);
        } finally {
            adminCli.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
