package com.omgservers.module.tenant.impl.operation.hasTenantPermission;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.module.tenant.factory.TenantModelFactory;
import com.omgservers.module.tenant.factory.TenantPermissionModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantPermissionModelFactory tenantPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenantPermission_whenHasTenantPermission_thenYes() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var permission = tenantPermissionModelFactory.create(tenant.getId(), userId(), TenantPermissionEnum.CREATE_PROJECT);
        upsertTenantPermissionOperation.upsertTenantPermission(TIMEOUT, pgPool, shard, permission);

        assertTrue(hasTenantPermissionOperation.hasTenantPermission(TIMEOUT, pgPool, shard, tenant.getId(), permission.getUserId(), permission.getPermission()));
    }

    @Test
    void givenUnknownUuids_whenHasTenantPermission_thenNo() {
        final var shard = 0;

        assertFalse(hasTenantPermissionOperation.hasTenantPermission(TIMEOUT, pgPool, shard, tenantId(), userId(), TenantPermissionEnum.CREATE_PROJECT));
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}