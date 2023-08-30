package com.omgservers.module.tenant.impl.operation.selectProject;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.factory.ProjectModelFactory;
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