package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.VerifyProjectPermissionExistsOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantProjectPermissionExistsOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    VerifyProjectPermissionExistsOperationTestInterface hasProjectPermissionOperation;

    @Inject
    UpsertProjectPermissionOperationTestInterface upsertProjectPermissionOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProjectPermission_whenVerifyTenantProjectPermission_Exists_thenTrue() {
        final var shard = 0;
        final var userId = userId();
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission = tenantProjectPermissionModelFactory.create(tenant.getId(), project.getId(), userId,
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);

        assertTrue(hasProjectPermissionOperation.hasProjectPermission(shard,
                tenant.getId(),
                project.getId(),
                permission.getUserId(),
                permission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenVerifyTenantProjectPermission_Exists_thenFalse() {
        final var shard = 0;

        assertFalse(hasProjectPermissionOperation.hasProjectPermission(shard,
                tenantId(),
                projectId(),
                userId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT));
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