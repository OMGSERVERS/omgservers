package com.omgservers.platform.integrationtest;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.TenantCli;
import com.omgservers.platforms.integrationtest.cli.UserCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
public class AdminApiCreateDeveloperTest extends Assertions {

    @Inject
    BootstrapEnvironmentOperation bootstrapEnvironmentOperation;

    @Inject
    AdminCli adminCli;

    @Inject
    TenantCli tenantCli;

    @Inject
    UserCli userCli;

    @Test
    void givenTenant_whenCreateDeveloper_thenUserCreatedWithCreateProjectPermission() {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        userCli.createClient();
        tenantCli.createClient();

        final var tenantUuid = adminCli.createTenant(tenantTitle());
        final var response = adminCli.createDeveloper(tenantUuid);
        final var userId = response.getUserId();
        final var password = response.getPassword();
        assertNotNull(userId);
        assertNotNull(password);

        assertTrue(tenantCli.hasTenantPermission(tenantUuid, userId, TenantPermissionEnum.CREATE_PROJECT));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }
}
