package com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class InsertStageOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenUpsertProject_thenInserted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        assertTrue(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenProject_whenUpsertProject_thenUpdated() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        assertFalse(upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
    }

    @Test
    void givenUnknownTenant_whenUpsertProject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var project = ProjectModel.create(tenantUuid(), ownerUuid(), ProjectConfigModel.create());
        final var exception = assertThrows(ServerSideNotFoundException.class, () ->
                upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}