package com.omgservers.module.tenant.impl.operation.upsertStage;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.StageModelFactory;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertStageOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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
    void givenProject_whenUpsertStage_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), matchmakerId(), StageConfigModel.create());
        assertTrue(upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage));
    }

    @Test
    void givenStage_whenUpsertStage_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(project.getId(), matchmakerId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage);

        assertFalse(upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage));
    }

    @Test
    void givenUnknownProjectUuid_whenUpsertStage_thenServerSideConflictException() {
        final var shard = 0;
        final var stage = stageModelFactory.create(projectId(), versionId(), UUID.randomUUID().toString(), matchmakerId(), StageConfigModel.create());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, stage));
        log.info("Exception: {}", exception.getMessage());
    }

    Long projectId() {
        return generateIdOperation.generateId();
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