package com.omgservers.application.module.tenantModule.impl.operation.hasStagePermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation.UpsertStagePermissionOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEntity;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class HasStagePermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    HasStagePermissionOperation hasStagePermissionOperation;

    @Inject
    UpsertStagePermissionOperation upsertStagePermissionOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    UpsertStageOperation upsertStageOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenStagePermission_whenHasStagePermission_thenYes() {
        final var shard = 0;
        final var userUuid = userUuid();
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), userUuid, ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = StageModel.create(project.getUuid());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);
        final var permission = StagePermissionEntity.create(stage.getUuid(), userUuid, StagePermissionEnum.CREATE_VERSION);
        upsertStagePermissionOperation.upsertStagePermission(TIMEOUT, pgPool, shard, permission);

        assertTrue(hasStagePermissionOperation.hasStagePermission(TIMEOUT, pgPool, shard, stage.getUuid(), permission.getUser(), permission.getPermission()));
    }

    @Test
    void givenUnknownUuids_whenHasStagePermission_thenNo() {
        final var shard = 0;

        assertFalse(hasStagePermissionOperation.hasStagePermission(TIMEOUT, pgPool, shard, projectUuid(), userUuid(), StagePermissionEnum.CREATE_VERSION));
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID projectUuid() {
        return UUID.randomUUID();
    }
}