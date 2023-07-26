package com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation;

import com.omgservers.application.module.tenantModule.impl.operation.selectTenantOperation.SelectTenantOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModelFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InsertTenantOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertTenantOperation insertTenantOperation;

    @Inject
    SelectTenantOperation selectTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenInsertTenant_thenInserted() {
        final var shard = 0;
        final var tenant1 = tenantModelFactory.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant1);

        final var tenant2 = selectTenantOperation.selectTenant(TIMEOUT, pgPool, shard, tenant1.getId());
        assertEquals(tenant1, tenant2);
    }

    @Test
    void givenTenant_whenInsertTenantAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        insertTenantOperation.insertTenant(TIMEOUT, pgPool, shard, tenant);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertTenantOperation
                .insertTenant(TIMEOUT, pgPool, shard, tenant));
        log.info("Exception: {}", exception.getMessage());
    }
}