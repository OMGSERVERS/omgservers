package com.omgservers.service.module.tenant.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.SelectTenantOperationTestInterface;
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
class SelectClientRuntimeOperationTest extends Assertions {

    @Inject
    SelectTenantOperationTestInterface selectTenantOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenSelectTenant_thenSelected() {
        final var shard = 0;
        final var tenant1 = tenantModelFactory.create();
        final var uuid = tenant1.getId();
        upsertTenantOperation.upsertTenant(shard, tenant1);

        final var tenant2 = selectTenantOperation.selectTenant(shard, uuid, false);
        assertEquals(tenant1, tenant2);
    }

    @Test
    void givenUnknownUuid_whenSelectTenant_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectTenantOperation
                .selectTenant(shard, id, false));
    }
}