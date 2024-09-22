package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.SelectStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantStagePermissionsByTenantIdOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectStageOperationTestInterface selectStageOperation;

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

    @Inject
    PgPool pgPool;

    @Test
    void givenStage_whenSelectStage_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var stage1 = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage1);

        final var stage2 = selectStageOperation.selectStage(shard, tenant.getId(), stage1.getId(), false);
        assertEquals(stage1, stage2);
    }

    @Test
    void givenUnknownIds_whenSelectStage_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectStageOperation
                .selectStage(shard, tenantId(), id, false));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}