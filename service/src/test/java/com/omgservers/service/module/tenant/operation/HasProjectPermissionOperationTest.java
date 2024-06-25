package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.ProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.HasProjectPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectPermissionOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HasProjectPermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    HasProjectPermissionOperationTestInterface hasProjectPermissionOperation;

    @Inject
    UpsertProjectPermissionOperationTestInterface upsertProjectPermissionOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    ProjectPermissionModelFactory projectPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProjectPermission_whenHasProjectPermission_thenTrue() {
        final var shard = 0;
        final var userId = userId();
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var permission = projectPermissionModelFactory.create(tenant.getId(), project.getId(), userId,
                ProjectPermissionEnum.STAGE_MANAGEMENT);
        upsertProjectPermissionOperation.upsertProjectPermission(shard, permission);

        assertTrue(hasProjectPermissionOperation.hasProjectPermission(shard,
                tenant.getId(),
                project.getId(),
                permission.getUserId(),
                permission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenHasProjectPermission_thenFalse() {
        final var shard = 0;

        assertFalse(hasProjectPermissionOperation.hasProjectPermission(shard,
                tenantId(),
                projectId(),
                userId(),
                ProjectPermissionEnum.STAGE_MANAGEMENT));
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