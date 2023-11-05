package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteStageOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteStageOperationTestInterface deleteStageOperation;

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

    @Test
    void givenStage_whenDeleteStage_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var changeContext = deleteStageOperation.deleteStage(shard, tenant.getId(), stage.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.STAGE_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteStage_thenFalse() {
        final var shard = 0;
        final var tenantId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteStageOperation.deleteStage(shard, tenantId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.STAGE_DELETED));
    }
}