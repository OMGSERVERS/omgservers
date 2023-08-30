package com.omgservers.module.tenant.impl.operation.upsertProjectPermission;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.ProjectPermissionModelFactory;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void givenProject_whenUpsertProjectPermission_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var permission = projectPermissionModelFactory.create(project.getId(), userId(), ProjectPermissionEnum.CREATE_STAGE);
        assertTrue(upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertProjectPermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var permission = projectPermissionModelFactory.create(project.getId(), userId(), ProjectPermissionEnum.CREATE_STAGE);
        upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertProjectPermissionOperation.upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownProjectUuid_whenUpsertProjectPermission_thenServerSideNotFoundException() {
        final var shard = 0;

        final var permission = projectPermissionModelFactory.create(projectId(), userId(), ProjectPermissionEnum.CREATE_STAGE);
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertProjectPermissionOperation
                .upsertProjectPermission(TIMEOUT, pgPool, shard, permission));
        log.info("Exception: {}", exception.getMessage());
    }

    Long ownerId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long projectId() {
        return generateIdOperation.generateId();
    }
}