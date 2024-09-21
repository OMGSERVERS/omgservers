package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.VerifyTenantPermissionExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HasTenantPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantPermissionExistsOperation verifyTenantPermissionExistsOperation;

    final PgPool pgPool;

    public Boolean hasTenantPermission(final int shard,
                                       Long tenantId,
                                       Long userId,
                                       TenantPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantPermissionExistsOperation
                        .execute(sqlConnection, shard, tenantId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
