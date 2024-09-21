package com.omgservers.service.module.tenant.operation;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.HasStagePermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStagePermissionOperationTestInterface;
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
class VerifyTenantStagePermissionExistsOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    HasStagePermissionOperationTestInterface hasStagePermissionOperation;

    @Inject
    UpsertStagePermissionOperationTestInterface upsertStagePermissionOperation;

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
    TenantStagePermissionModelFactory tenantStagePermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenStagePermission_whenVerifyTenantStagePermission_Exists_thenTrue() {
        final var shard = 0;
        final var userId = userId();
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var permission = tenantStagePermissionModelFactory.create(tenant.getId(), stage.getId(), userId,
                TenantStagePermissionEnum.VERSION_MANAGEMENT);
        upsertStagePermissionOperation.upsertStagePermission(shard, permission);

        assertTrue(hasStagePermissionOperation.verifyTenantStagePermissionExists(shard,
                tenant.getId(),
                stage.getId(),
                permission.getUserId(),
                permission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenVerifyTenantStagePermission_Exists_thenFalse() {
        final var shard = 0;

        assertFalse(hasStagePermissionOperation.verifyTenantStagePermissionExists(shard,
                tenantId(),
                projectId(),
                userId(),
                TenantStagePermissionEnum.VERSION_MANAGEMENT));
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long projectId() {
        return generateIdOperation.generateId();
    }
}