package com.omgservers.service.module.tenant.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantStageOperationTest extends Assertions {

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenStage_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        final var changeContext = upsertStageOperation.upsertStage(shard, stage);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_STAGE_CREATED));
    }

    @Test
    void givenStage_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var changeContext = upsertStageOperation.upsertStage(shard, stage);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_STAGE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var stage = tenantStageModelFactory.create(tenantId(), projectId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertStageOperation.upsertStage(shard, stage));
    }

    @Test
    void givenStage_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var stage1 = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage1);

        final var stage2 = tenantStageModelFactory.create(tenant.getId(), project.getId(), stage1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertStageOperation.upsertStage(shard, stage2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long projectId() {
        return generateIdOperation.generateId();
    }
}