package com.omgservers.application.module.tenantModule.impl.operation.upsertProjectPermissionOperation;

import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertProjectPermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertProjectPermissionOperation upsertProjectPermissionOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenUpsertProjectPermission_thenInserted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var permission = ProjectPermissionModel.create(project.getUuid(), userUuid(), ProjectPermissionEnum.CREATE_STAGE);
        assertTrue(upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertProjectPermission_thenUpdated() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var permission = ProjectPermissionModel.create(project.getUuid(), userUuid(), ProjectPermissionEnum.CREATE_STAGE);
        upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownProjectUuid_whenUpsertProjectPermission_thenServerSideNotFoundException() {
        final var shard = 0;

        final var permission = ProjectPermissionModel.create(projectUuid(), userUuid(), ProjectPermissionEnum.CREATE_STAGE);
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertProjectPermissionOperation
                .upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID projectUuid() {
        return UUID.randomUUID();
    }
}