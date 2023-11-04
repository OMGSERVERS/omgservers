package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertProjectOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenUpsertProject_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        assertTrue(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenProject_whenUpsertProject_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        assertFalse(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenUnknownTenant_whenUpsertProject_thenException() {
        final var shard = 0;
        final var project = projectModelFactory.create(tenantId(), ProjectConfigModel.create());
        assertThrows(ServerSideConflictException.class, () ->
                upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}