package com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEntity;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
    PgPool pgPool;

    @Test
    void givenStage_whenUpsertStagePermission_thenInserted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = StageModel.create(project.getUuid());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);

        final var permission = StagePermissionEntity.create(stage.getUuid(), userUuid(), StagePermissionEnum.CREATE_VERSION);
        assertTrue(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertStagePermission_thenUpdated() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = StageModel.create(project.getUuid());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);
        final var permission = StagePermissionEntity.create(stage.getUuid(), userUuid(), StagePermissionEnum.CREATE_VERSION);
        upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownStageUuid_whenUpsertStagePermission_thenServerSideNotFoundException() {
        final var shard = 0;

        final var permission = StagePermissionEntity.create(stageUuid(), userUuid(), StagePermissionEnum.CREATE_VERSION);
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertStagePermissionOperation
                .upsertStagePermission(TIMEOUT, pgPool, shard, permission));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}