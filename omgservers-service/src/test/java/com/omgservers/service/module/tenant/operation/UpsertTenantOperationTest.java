package com.omgservers.service.module.tenant.operation;

import com.omgservers.service.factory.TenantModelFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
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

    @Inject
    PgPool pgPool;

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
}