package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void whenUpsertTenant_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        assertTrue(upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant));
    }

    @Test
    void givenTenant_whenUpsertTenant_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        assertFalse(upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant));
    }
}