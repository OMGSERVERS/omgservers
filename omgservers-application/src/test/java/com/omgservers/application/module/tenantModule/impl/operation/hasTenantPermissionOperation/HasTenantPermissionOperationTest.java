package com.omgservers.application.module.tenantModule.impl.operation.hasTenantPermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantPermissionOperation.UpsertTenantPermissionOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class HasTenantPermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    HasTenantPermissionOperation hasTenantPermissionOperation;

    @Inject
    UpsertTenantPermissionOperation upsertTenantPermissionOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenantPermission_whenHasTenantPermission_thenYes() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var permission = TenantPermissionModel.create(tenant.getUuid(), userUuid(), TenantPermissionEnum.CREATE_PROJECT);
        upsertTenantPermissionOperation.upsertTenantPermission(TIMEOUT, pgPool, shard, permission);

        assertTrue(hasTenantPermissionOperation.hasTenantPermission(TIMEOUT, pgPool, shard, tenant.getUuid(), permission.getUser(), permission.getPermission()));
    }

    @Test
    void givenUnknownUuids_whenHasTenantPermission_thenNo() {
        final var shard = 0;

        assertFalse(hasTenantPermissionOperation.hasTenantPermission(TIMEOUT, pgPool, shard, tenantUuid(), userUuid(), TenantPermissionEnum.CREATE_PROJECT));
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }
}