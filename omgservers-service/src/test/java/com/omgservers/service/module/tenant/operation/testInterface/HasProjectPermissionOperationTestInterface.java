package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.VerifyTenantProjectPermissionExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HasProjectPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantProjectPermissionExistsOperation verifyTenantProjectPermissionExistsOperation;

    final PgPool pgPool;

    public Boolean hasProjectPermission(final int shard,
                                       Long tenantId,
                                       Long projectId,
                                       Long userId,
                                       TenantProjectPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantProjectPermissionExistsOperation
                        .execute(sqlConnection, shard, tenantId, projectId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
