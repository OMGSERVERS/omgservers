package com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.base.factory.ProjectModelFactory;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.base.factory.TenantModelFactory;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

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
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenSelectProject_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project1 = projectModelFactory.create(tenant.getId(), ownerId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project1);

        final var project2 = selectProjectOperation.selectProject(TIMEOUT, pgPool, shard, project1.getId());
        assertEquals(project1, project2);
    }

    @Test
    void givenUnknownUuid_whenSelectProject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectProjectOperation
                .selectProject(TIMEOUT, pgPool, shard, id));
    }

    Long ownerId() {
        return generateIdOperation.generateId();
    }
}