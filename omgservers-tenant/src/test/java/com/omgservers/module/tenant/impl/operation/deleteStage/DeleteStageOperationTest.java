package com.omgservers.module.tenant.impl.operation.deleteStage;

import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.StageModelFactory;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
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
class DeleteStageOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteStageOperation deleteStageOperation;

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
    GenerateIdOperation generateIdOperation;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenantProjectStage_whenDeleteStage_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var stage = stageModelFactory.create(project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);

        assertTrue(deleteStageOperation.deleteStage(TIMEOUT, pgPool, shard, stage.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteStage_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteStageOperation.deleteStage(TIMEOUT, pgPool, shard, id));
    }
}