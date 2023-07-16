package com.omgservers.application.module.tenantModule.impl.operation.insertStageOperation;

import com.omgservers.application.module.tenantModule.impl.operation.insertProjectOperation.InsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation.InsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.selectStageOperation.SelectStageOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideConflictException;
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
class InsertStageOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertStageOperation insertStageOperation;

    @Inject
    InsertProjectOperation insertProjectOperation;

    @Inject
    InsertTenantOperation insertTenantOperation;

    @Inject
    SelectStageOperation selectStageOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenInsertStage_thenInserted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        insertProjectOperation.insertProject(TIMEOUT, pgPool, shard, project);

        final var stage1 = StageModel.create(project.getUuid());
        insertStageOperation.insertStage(TIMEOUT, pgPool, shard, stage1);

        final var stage2 = selectStageOperation.selectStage(TIMEOUT, pgPool, shard, stage1.getUuid());
        assertEquals(stage1, stage2);
    }

    @Test
    void givenStage_whenInsertStageAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        insertProjectOperation.insertProject(TIMEOUT, pgPool, shard, project);
        final var stage = StageModel.create(project.getUuid());
        insertStageOperation.insertStage(TIMEOUT, pgPool, shard, stage);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertStageOperation
                .insertStage(TIMEOUT, pgPool, shard, stage));
        log.info("Exception: {}", exception.getMessage());
    }

    @Test
    void givenUnknownProjectUuid_whenInsertStage_thenServerSideNotFoundException() {
        final var shard = 0;
        final var stage = StageModel.create(projectUuid());
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> insertStageOperation
                .insertStage(TIMEOUT, pgPool, shard, stage));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID projectUuid() {
        return UUID.randomUUID();
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}