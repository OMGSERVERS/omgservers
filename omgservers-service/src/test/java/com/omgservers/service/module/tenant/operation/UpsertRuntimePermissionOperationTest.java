package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.TenantPermissionModelFactory;
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
class UpsertRuntimePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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
    void givenTenantPermission_whenUpsertTenantPermission_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var permission = tenantPermissionModelFactory.create(tenant.getId(),
                userId(),
                TenantPermissionEnum.CREATE_PROJECT);

        final var changeContext = upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenantPermission_whenUpsertTenantPermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var permission = tenantPermissionModelFactory.create(tenant.getId(),
                userId(),
                TenantPermissionEnum.CREATE_PROJECT);
        upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);

        final var changeContext = upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertTenantPermission_thenException() {
        final var shard = 0;

        final var permission = tenantPermissionModelFactory.create(tenantId(),
                userId(),
                TenantPermissionEnum.CREATE_PROJECT);
        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantPermissionOperation
                .upsertTenantPermission(shard, permission));
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}