package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientRuntimeOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

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

        final var project = projectModelFactory.create(tenant.getId());
        final var changeContext = upsertProjectOperation.upsertProject(shard, project);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.PROJECT_CREATED));
    }

    @Test
    void givenProject_whenUpsertProject_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var changeContext = upsertProjectOperation.upsertProject(shard, project);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.PROJECT_CREATED));
    }

    @Test
    void givenUnknownTenant_whenUpsertProject_thenException() {
        final var shard = 0;
        final var project = projectModelFactory.create(tenantId());
        assertThrows(ServerSideConflictException.class, () ->
                upsertProjectOperation.upsertProject(shard, project));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}