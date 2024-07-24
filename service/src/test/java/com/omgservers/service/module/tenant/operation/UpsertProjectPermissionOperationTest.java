package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.ProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertProjectPermissionOperationTest extends Assertions {
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
    ProjectModelFactory projectModelFactory;

    @Inject
    ProjectPermissionModelFactory projectPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProjectPermission_whenUpsertProjectPermission_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var permission = projectPermissionModelFactory.create(tenant.getId(), project.getId(), userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT);

        final var changeContext = upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenProjectPermission_whenUpsertProjectPermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission = projectPermissionModelFactory.create(tenant.getId(), project.getId(), userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);

        final var changeContext = upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertProjectPermission_thenException() {
        final var shard = 0;

        final var permission = projectPermissionModelFactory.create(tenantId(), projectId(), userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT);
        assertThrows(ServerSideBadRequestException.class, () -> upsertProjectPermissionOperation
                .upsertProjectPermission(shard, permission));
    }

    @Test
    void givenProjectPermission_whenUpsertProjectPermission_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission1 = projectPermissionModelFactory.create(tenant.getId(),
                project.getId(),
                userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission1);

        final var permission2 = projectPermissionModelFactory.create(tenant.getId(),
                project.getId(),
                userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT,
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