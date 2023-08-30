package com.omgservers.module.tenant.impl.operation.selectTenant;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectTenantOperation selectTenantOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenSelectTenant_thenSelected() {
        final var shard = 0;
        final var tenant1 = tenantModelFactory.create(TenantConfigModel.create());
        final var uuid = tenant1.getId();
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant1);

        final var tenant2 = selectTenantOperation.selectTenant(TIMEOUT, pgPool, shard, uuid);
        assertEquals(tenant1, tenant2);
    }

    @Test
    void givenUnknownUuid_whenSelectTenant_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectTenantOperation
                .selectTenant(TIMEOUT, pgPool, shard, id));
    }
}