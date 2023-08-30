package com.omgservers.module.tenant.impl.operation.selectStage;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.StageModelFactory;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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