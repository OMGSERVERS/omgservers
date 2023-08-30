package com.omgservers.module.tenant.impl.operation.upsertTenantPermission;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.factory.TenantModelFactory;
import com.omgservers.factory.TenantPermissionModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantPermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertTenantPermissionOperation upsertTenantPermissionOperation;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantPermissionModelFactory tenantPermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenTenant_whenUpsertTenantPermission_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);

        final var permission = tenantPermissionModelFactory.create(tenant.getId(), userId(), TenantPermissionEnum.CREATE_PROJECT);
        assertTrue(upsertTenantPermissionOperation.upsertTenantPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenPermission_whenUpsertTenantPermission_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var permission = tenantPermissionModelFactory.create(tenant.getId(), userId(), TenantPermissionEnum.CREATE_PROJECT);
        upsertTenantPermissionOperation.upsertTenantPermission(TIMEOUT, pgPool, shard, permission);

        assertFalse(upsertTenantPermissionOperation.upsertTenantPermission(TIMEOUT, pgPool, shard, permission));
    }

    @Test
    void givenUnknownTenantUuid_whenUpsertTenantPermission_thenServerSideNotFoundException() {
        final var shard = 0;

        final var permission = tenantPermissionModelFactory.create(tenantId(), userId(), TenantPermissionEnum.CREATE_PROJECT);
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertTenantPermissionOperation
                .upsertTenantPermission(TIMEOUT, pgPool, shard, permission));
        log.info("Exception: {}", exception.getMessage());
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}