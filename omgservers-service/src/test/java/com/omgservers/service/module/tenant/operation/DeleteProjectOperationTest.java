package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteProjectOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteProjectOperationTestInterface deleteProjectOperation;

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
    void givenProject_whenDeleteProject_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        final var id = project.getId();
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);

        final var changeContext = deleteProjectOperation.deleteProject(shard, tenant.getId(), id);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.PROJECT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteProject_thenFalse() {
        final var shard = 0;
        final var tenantId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteProjectOperation.deleteProject(shard, tenantId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.PROJECT_DELETED));
    }
}