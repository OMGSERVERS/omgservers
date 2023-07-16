package com.omgservers.application.module.tenantModule.impl.operation.selectStageOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectProjectStagesOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectStageOperation selectStageOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    UpsertStageOperation upsertStageOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenStage_whenSelectStage_thenSelected() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var stage1 = StageModel.create(project.getUuid());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage1);

        final var stage2 = selectStageOperation.selectStage(TIMEOUT, pgPool, shard, stage1.getUuid());
        assertEquals(stage1, stage2);
    }

    @Test
    void givenUnknownUuid_whenSelectStage_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectStageOperation
                .selectStage(TIMEOUT, pgPool, shard, uuid));
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}