package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.StagePermissionModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertStagePermission.UpsertStagePermissionOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertStagePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertStagePermissionOperation upsertStagePermissionOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

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
    void givenStage_whenUpsertStagePermission_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var permission = stagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId(),
                StagePermissionEnum.VERSION_MANAGEMENT);
        assertTrue(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertStagePermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var permission = stagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId(),
                StagePermissionEnum.VERSION_MANAGEMENT);
        upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownIds_whenUpsertStagePermission_thenException() {
        final var shard = 0;

        final var permission =
                stagePermissionModelFactory.create(tenantId(), stageId(), userId(),
                        StagePermissionEnum.VERSION_MANAGEMENT);
        assertThrows(ServerSideConflictException.class, () -> upsertStagePermissionOperation
                .upsertStagePermission(TIMEOUT, pgPool, shard, permission));
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