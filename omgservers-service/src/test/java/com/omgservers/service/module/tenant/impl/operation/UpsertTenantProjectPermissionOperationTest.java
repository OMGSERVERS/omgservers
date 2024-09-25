package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantProjectPermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertProjectPermissionOperationTestInterface upsertProjectPermissionOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProjectPermission_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var permission = tenantProjectPermissionModelFactory.create(tenant.getId(), project.getId(), userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT);

        final var changeContext = upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenProjectPermission_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission = tenantProjectPermissionModelFactory.create(tenant.getId(), project.getId(), userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);

        final var changeContext = upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;

        final var permission = tenantProjectPermissionModelFactory.create(tenantId(), projectId(), userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT);
        assertThrows(ServerSideBadRequestException.class, () -> upsertProjectPermissionOperation
                .upsertProjectPermission(shard, permission));
    }

    @Test
    void givenProjectPermission_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission1 = tenantProjectPermissionModelFactory.create(tenant.getId(),
                project.getId(),
                userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission1);

        final var permission2 = tenantProjectPermissionModelFactory.create(tenant.getId(),
                project.getId(),
                userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT,
                permission1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertProjectPermissionOperation.upsertProjectPermission(shard, permission2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long projectId() {
        return generateIdOperation.generateId();
    }
}