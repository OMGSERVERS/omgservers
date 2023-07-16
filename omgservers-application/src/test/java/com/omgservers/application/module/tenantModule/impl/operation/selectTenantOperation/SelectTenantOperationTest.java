package com.omgservers.application.module.tenantModule.impl.operation.selectTenantOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectTenantOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectTenantOperation selectTenantOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenSelectTenant_thenSelected() {
        final var shard = 0;
        final var tenant1 = TenantModel.create(TenantConfigModel.create());
        final var uuid = tenant1.getUuid();
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant1);

        final var tenant2 = selectTenantOperation.selectTenant(TIMEOUT, pgPool, shard, uuid);
        assertEquals(tenant1, tenant2);
    }

    @Test
    void givenUnknownUuid_whenSelectTenant_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectTenantOperation
                .selectTenant(TIMEOUT, pgPool, shard, uuid));
    }
}