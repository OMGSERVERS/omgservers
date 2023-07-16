package com.omgservers.platform.integrationtest;

import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
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
        final var user = response.getUser();
        final var password = response.getPassword();
        assertNotNull(user);
        assertNotNull(password);

        assertTrue(tenantCli.hasTenantPermission(tenantUuid, user, TenantPermissionEnum.CREATE_PROJECT));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }
}
