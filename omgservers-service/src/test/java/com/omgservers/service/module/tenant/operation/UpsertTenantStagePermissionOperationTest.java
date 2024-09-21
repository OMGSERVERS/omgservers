package com.omgservers.service.module.tenant.operation;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStagePermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantStagePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertStagePermissionOperationTestInterface upsertStagePermissionOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    TenantStagePermissionModelFactory tenantStagePermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenStage_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var permission = tenantStagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT);
        final var changeContext = upsertStagePermissionOperation.upsertStagePermission(shard, permission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenPermission_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var permission = tenantStagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT);
        upsertStagePermissionOperation.upsertStagePermission(shard, permission);

        final var changeContext = upsertStagePermissionOperation.upsertStagePermission(shard, permission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;

        final var permission = tenantStagePermissionModelFactory.create(tenantId(), stageId(), userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT);
        assertThrows(ServerSideBadRequestException.class, () -> upsertStagePermissionOperation
                .upsertStagePermission(shard, permission));
    }

    @Test
    void givenPermission_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var permission1 = tenantStagePermissionModelFactory.create(tenant.getId(),
                stage.getId(),
                userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT);
        upsertStagePermissionOperation.upsertStagePermission(shard, permission1);

        final var permission2 = tenantStagePermissionModelFactory.create(tenant.getId(),
                stage.getId(),
                userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT,
                permission1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertStagePermissionOperation.upsertStagePermission(shard, permission2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}