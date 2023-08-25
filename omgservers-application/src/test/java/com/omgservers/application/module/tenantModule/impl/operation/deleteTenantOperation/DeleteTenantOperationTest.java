package com.omgservers.application.module.tenantModule.impl.operation.deleteTenantOperation;

import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.base.factory.TenantModelFactory;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class DeleteTenantOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteTenantOperation deleteTenantOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenDeleteTenant_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        assertTrue(deleteTenantOperation.deleteTenant(TIMEOUT, pgPool, shard, tenant.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteTenantOperation.deleteTenant(TIMEOUT, pgPool, shard, id));
    }
}