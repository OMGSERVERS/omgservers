package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Test
    void whenUpsertTenant_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        final var changeContext = upsertTenantOperation.upsertTenant(shard, tenant);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenant_whenUpsertTenant_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var changeContext = upsertTenantOperation.upsertTenant(shard, tenant);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenTenant_whenUpsertTenant_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant1 = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant1);

        final var tenant2 = tenantModelFactory.create(tenant1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantOperation.upsertTenant(shard, tenant2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}