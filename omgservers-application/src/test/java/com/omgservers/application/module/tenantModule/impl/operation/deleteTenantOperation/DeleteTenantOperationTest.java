package com.omgservers.application.module.tenantModule.impl.operation.deleteTenantOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteTenantOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteTenantOperation deleteTenantOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenDeleteTenant_thenDeleted() {
        final var shard = 0;
        final var tenant = TenantModel.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        assertTrue(deleteTenantOperation.deleteTenant(TIMEOUT, pgPool, shard, tenant.getUuid()));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteTenantOperation.deleteTenant(TIMEOUT, pgPool, shard, uuid));
    }
}