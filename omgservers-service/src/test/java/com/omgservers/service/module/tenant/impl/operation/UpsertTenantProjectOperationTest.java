package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantProjectOperationTest extends Assertions {

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

    @Test
    void givenProject_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var project = tenantProjectModelFactory.create(tenant.getId());
        final var changeContext = upsertProjectOperation.upsertProject(shard, project);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_CREATED));
    }

    @Test
    void givenProject_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);

        final var changeContext = upsertProjectOperation.upsertProject(shard, project);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_CREATED));
    }

    @Test
    void givenUnknownTenantId_whenExecute_thenException() {
        final var shard = 0;
        final var project = tenantProjectModelFactory.create(tenantId());
        assertThrows(ServerSideBadRequestException.class, () -> upsertProjectOperation.upsertProject(shard, project));
    }

    @Test
    void givenProject_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project1 = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project1);

        final var project2 = tenantProjectModelFactory.create(tenant.getId(), project1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertProjectOperation.upsertProject(shard, project2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}