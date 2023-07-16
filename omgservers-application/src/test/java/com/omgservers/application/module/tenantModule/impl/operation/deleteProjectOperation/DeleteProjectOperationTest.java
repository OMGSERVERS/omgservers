package com.omgservers.application.module.tenantModule.impl.operation.deleteProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteProjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteProjectOperation deleteProjectOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenDeleteProject_thenDeleted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        final var uuid = project.getUuid();
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        assertTrue(deleteProjectOperation.deleteProject(TIMEOUT, pgPool, shard, uuid));
    }

    @Test
    void givenUnknownUuid_whenDeleteProject_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteProjectOperation.deleteProject(TIMEOUT, pgPool, shard, uuid));
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}