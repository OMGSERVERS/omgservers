package com.omgservers.module.tenant.impl.operation.upsertStagePermission;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.StageModelFactory;
import com.omgservers.factory.StagePermissionModelFactory;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);

        final var permission = stagePermissionModelFactory.create(stage.getId(), userId(), StagePermissionEnum.CREATE_VERSION);
        assertTrue(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertStagePermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), StageConfigModel.create());
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

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}