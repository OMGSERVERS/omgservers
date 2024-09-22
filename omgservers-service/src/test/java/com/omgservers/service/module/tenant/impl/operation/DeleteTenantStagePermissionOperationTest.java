package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.DeleteStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantStagePermissionOperationTest extends Assertions {
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
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenStage_whenDeleteStage_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);

        final var changeContext = deleteStageOperation.deleteStage(shard, tenant.getId(), stage.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_STAGE_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteStage_thenFalse() {
        final var shard = 0;
        final var tenantId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteStageOperation.deleteStage(shard, tenantId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_STAGE_DELETED));
    }
}