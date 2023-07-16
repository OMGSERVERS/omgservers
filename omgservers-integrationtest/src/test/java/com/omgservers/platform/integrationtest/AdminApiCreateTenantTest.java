package com.omgservers.platform.integrationtest;

import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
public class AdminApiCreateTenantTest extends Assertions {

    @Inject
    BootstrapEnvironmentOperation bootstrapEnvironmentOperation;

    @Inject
    AdminCli adminCli;

    @Test
    void givenEnvironment_whenCreateTenant_thenTenantCreated() {
        bootstrapEnvironmentOperation.bootstrap();

        adminCli.createClient();
        assertNotNull(adminCli.createTenant(tenantTitle()));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }
}
