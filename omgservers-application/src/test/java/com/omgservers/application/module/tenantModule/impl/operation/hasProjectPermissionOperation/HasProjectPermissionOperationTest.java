package com.omgservers.application.module.tenantModule.impl.operation.hasProjectPermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectPermissionOperation.UpsertProjectPermissionOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class HasProjectPermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    HasProjectPermissionOperation hasProjectPermissionOperation;

    @Inject
    UpsertProjectPermissionOperation upsertProjectPermissionOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProjectPermission_whenHasProjectPermission_thenYes() {
        final var shard = 0;
        final var userUuid = userUuid();
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), userUuid, ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var permission = ProjectPermissionModel.create(project.getUuid(), userUuid, ProjectPermissionEnum.CREATE_STAGE);
        upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission);

        assertTrue(hasProjectPermissionOperation.hasProjectPermission(TIMEOUT, pgPool, shard, project.getUuid(), permission.getUser(), permission.getPermission()));
    }

    @Test
    void givenUnknownUuids_whenHasProjectPermission_thenNo() {
        final var shard = 0;

        assertFalse(hasProjectPermissionOperation.hasProjectPermission(TIMEOUT, pgPool, shard, projectUuid(), userUuid(), ProjectPermissionEnum.CREATE_STAGE));
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID projectUuid() {
        return UUID.randomUUID();
    }
}