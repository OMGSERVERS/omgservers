package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionRuntimeOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

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
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());

        final var changeContext = upsertStageOperation.upsertStage(shard, stage);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.STAGE_CREATED));
    }

    @Test
    void givenStage_whenUpsertStage_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var changeContext = upsertStageOperation.upsertStage(shard, stage);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.STAGE_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertStage_thenException() {
        final var shard = 0;
        final var stage = stageModelFactory.create(tenantId(), projectId());
        assertThrows(ServerSideConflictException.class, () ->
                upsertStageOperation.upsertStage(shard, stage));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
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
}