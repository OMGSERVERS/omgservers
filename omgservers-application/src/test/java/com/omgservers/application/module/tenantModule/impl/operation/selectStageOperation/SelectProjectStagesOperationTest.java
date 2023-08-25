package com.omgservers.application.module.tenantModule.impl.operation.selectStageOperation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.base.factory.ProjectModelFactory;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.base.factory.StageModelFactory;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.base.factory.TenantModelFactory;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

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
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenStage_whenSelectStage_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var stage1 = stageModelFactory.create(project.getId(), matchmakerId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage1);

        final var stage2 = selectStageOperation.selectStage(TIMEOUT, pgPool, shard, stage1.getId());
        assertEquals(stage1, stage2);
    }

    @Test
    void givenUnknownUuid_whenSelectStage_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectStageOperation
                .selectStage(TIMEOUT, pgPool, shard, id));
    }

    Long ownerId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }
}