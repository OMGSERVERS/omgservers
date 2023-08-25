package com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.base.factory.ProjectModelFactory;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.base.factory.StageModelFactory;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.base.factory.StagePermissionModelFactory;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.base.factory.TenantModelFactory;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertStagePermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertStagePermissionOperation upsertStagePermissionOperation;

    @Inject
    UpsertStageOperation upsertStageOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

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
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), matchmakerId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);

        final var permission = stagePermissionModelFactory.create(stage.getId(), userId(), StagePermissionEnum.CREATE_VERSION);
        assertTrue(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertStagePermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), matchmakerId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);
        final var permission = stagePermissionModelFactory.create(stage.getId(), userId(), StagePermissionEnum.CREATE_VERSION);
        upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownStageUuid_whenUpsertStagePermission_thenServerSideNotFoundException() {
        final var shard = 0;

        final var permission = stagePermissionModelFactory.create(stageId(), userId(), StagePermissionEnum.CREATE_VERSION);
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertStagePermissionOperation
                .upsertStagePermission(TIMEOUT, pgPool, shard, permission));
        log.info("Exception: {}", exception.getMessage());
    }

    Long projectId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long ownerId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}