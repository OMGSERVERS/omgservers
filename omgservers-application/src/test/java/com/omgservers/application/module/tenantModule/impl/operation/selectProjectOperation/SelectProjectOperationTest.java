package com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectProjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectProjectOperation selectProjectOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenSelectProject_thenSelected() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project1 = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project1);

        final var project2 = selectProjectOperation.selectProject(TIMEOUT, pgPool, shard, project1.getUuid());
        assertEquals(project1, project2);
    }

    @Test
    void givenUnknownUuid_whenSelectProject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectProjectOperation
                .selectProject(TIMEOUT, pgPool, shard, uuid));
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}