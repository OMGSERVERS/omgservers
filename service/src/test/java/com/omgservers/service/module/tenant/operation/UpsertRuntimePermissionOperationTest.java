package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
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
                TenantPermissionEnum.PROJECT_MANAGEMENT);

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
                TenantPermissionEnum.PROJECT_MANAGEMENT);
        upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);

        final var changeContext = upsertTenantPermissionOperation.upsertTenantPermission(shard, permission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertTenantPermission_thenException() {
        final var shard = 0;

        final var permission = tenantPermissionModelFactory.create(tenantId(),
                userId(),
                TenantPermissionEnum.PROJECT_MANAGEMENT);
        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantPermissionOperation
                .upsertTenantPermission(shard, permission));
    }

    @Test
    void givenTenantPermission_whenUpsertTenantPermission_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var permission1 = tenantPermissionModelFactory.create(tenant.getId(),
                userId(),
                TenantPermissionEnum.PROJECT_MANAGEMENT);
        upsertTenantPermissionOperation.upsertTenantPermission(shard, permission1);

        final var permission2 = tenantPermissionModelFactory.create(tenant.getId(),
                userId(),
                TenantPermissionEnum.PROJECT_MANAGEMENT,
                permission1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantPermissionOperation.upsertTenantPermission(shard, permission2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}