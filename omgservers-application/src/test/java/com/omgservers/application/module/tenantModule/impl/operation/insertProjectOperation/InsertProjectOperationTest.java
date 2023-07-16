package com.omgservers.application.module.tenantModule.impl.operation.insertProjectOperation;

import com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation.InsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation.SelectProjectOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideConflictException;
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
class InsertProjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertProjectOperation insertProjectOperation;

    @Inject
    InsertTenantOperation insertTenantOperation;

    @Inject
    SelectProjectOperation selectProjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenInsertProject_thenInserted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant);

        final var project1 = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        insertProjectOperation.insertProject(TIMEOUT, pgPool, shard, project1);

        final var project2 = selectProjectOperation.selectProject(TIMEOUT, pgPool, shard, project1.getUuid());
        assertEquals(project1, project2);
    }

    @Test
    void givenProject_whenInsertProjectAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = ProjectModel.create(tenant.getUuid(), ownerUuid(), ProjectConfigModel.create());
        insertProjectOperation.insertProject(TIMEOUT, pgPool, shard, project);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertProjectOperation
                .insertProject(TIMEOUT, pgPool, shard, project));
        log.info("Exception: {}", exception.getMessage());
    }

    @Test
    void givenUnknownTenantUuid_whenInsertProject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var project = ProjectModel.create(tenantUuid(), ownerUuid(), ProjectConfigModel.create());
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> insertProjectOperation
                .insertProject(TIMEOUT, pgPool, shard, project));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID ownerUuid() {
        return UUID.randomUUID();
    }
}