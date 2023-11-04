package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteTenantOperationTestInterface deleteTenantOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenDeleteTenant_thenDeleted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var changeContext = deleteTenantOperation.deleteTenant(shard, tenant.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteTenant_thenFalse() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteTenantOperation.deleteTenant(shard, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_DELETED));
    }
}