package com.omgservers.module.tenant.operation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.module.tenant.factory.ProjectModelFactory;
import com.omgservers.module.tenant.factory.ProjectPermissionModelFactory;
import com.omgservers.module.tenant.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.hasProjectPermission.HasProjectPermissionOperation;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertProjectPermission.UpsertProjectPermissionOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HasProjectPermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    HasProjectPermissionOperation hasProjectPermissionOperation;

    @Inject
    UpsertProjectPermissionOperation upsertProjectPermissionOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    ProjectPermissionModelFactory projectPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProjectPermission_whenHasProjectPermission_thenYes() {
        final var shard = 0;
        final var userId = userId();
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var permission = projectPermissionModelFactory.create(tenant.getId(), project.getId(), userId, ProjectPermissionEnum.CREATE_STAGE);
        upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission);

        assertTrue(hasProjectPermissionOperation.hasProjectPermission(TIMEOUT, pgPool, shard, tenant.getId(), project.getId(), permission.getUserId(), permission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenHasProjectPermission_thenFalse() {
        final var shard = 0;

        assertFalse(hasProjectPermissionOperation.hasProjectPermission(TIMEOUT, pgPool, shard, tenantId(), projectId(), userId(), ProjectPermissionEnum.CREATE_STAGE));
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