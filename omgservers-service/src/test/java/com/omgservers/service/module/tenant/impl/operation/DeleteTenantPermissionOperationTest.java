package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.DeleteProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantPermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteProjectOperationTestInterface deleteProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenProject_whenDeleteProject_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        final var id = project.getId();
        upsertProjectOperation.upsertProject(shard, project);

        final var changeContext = deleteProjectOperation.deleteProject(shard, tenant.getId(), id);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteProject_thenFalse() {
        final var shard = 0;
        final var tenantId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteProjectOperation.deleteProject(shard, tenantId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_DELETED));
    }
}