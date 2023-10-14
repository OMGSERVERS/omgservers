package com.omgservers.module.tenant.impl.operation.upsertProject;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.module.tenant.factory.ProjectModelFactory;
import com.omgservers.module.tenant.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
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
    UpsertTenantOperation upsertTenantOperation;

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
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        assertTrue(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenProject_whenUpsertProject_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        assertFalse(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenUnknownTenant_whenUpsertProject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var project = projectModelFactory.create(tenantId(), ProjectConfigModel.create());
        final var exception = assertThrows(ServerSideNotFoundException.class, () ->
                upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}