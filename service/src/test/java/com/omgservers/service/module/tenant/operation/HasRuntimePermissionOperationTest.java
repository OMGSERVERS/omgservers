package com.omgservers.service.module.tenant.operation;

import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.HasTenantPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantPermissionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HasRuntimePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    HasTenantPermissionOperationTestInterface hasTenantPermissionOperation;

    @Inject
    UpsertTenantPermissionOperationTestInterface upsertTenantPermissionOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

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
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var permission =
                tenantPermissionModelFactory.create(tenant.getId(), userId(), TenantPermissionEnum.PROJECT_MANAGEMENT);
        upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);

        assertTrue(hasTenantPermissionOperation.hasTenantPermission(shard,
                tenant.getId(),
                permission.getUserId(),
                permission.getPermission()));
    }

    @Test
    void givenUnknownUuids_whenHasTenantPermission_thenNo() {
        final var shard = 0;

        assertFalse(hasTenantPermissionOperation.hasTenantPermission(shard,
                tenantId(),
                userId(),
                TenantPermissionEnum.PROJECT_MANAGEMENT));
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}