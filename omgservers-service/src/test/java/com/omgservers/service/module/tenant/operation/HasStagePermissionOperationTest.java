package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.StagePermissionModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.HasStagePermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStagePermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HasStagePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    HasStagePermissionOperationTestInterface hasStagePermissionOperation;

    @Inject
    UpsertStagePermissionOperationTestInterface upsertStagePermissionOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    StagePermissionModelFactory stagePermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenStagePermission_whenHasStagePermission_thenTrue() {
        final var shard = 0;
        final var userId = userId();
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var permission = stagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId,
                StagePermissionEnum.VERSION_MANAGEMENT);
        upsertStagePermissionOperation.upsertStagePermission(shard, permission);

        assertTrue(hasStagePermissionOperation.hasStagePermission(shard,
                tenant.getId(),
                stage.getId(),
                permission.getUserId(),
                permission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenHasStagePermission_thenFalse() {
        final var shard = 0;

        assertFalse(hasStagePermissionOperation.hasStagePermission(shard,
                tenantId(),
                projectId(),
                userId(),
                StagePermissionEnum.VERSION_MANAGEMENT));
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