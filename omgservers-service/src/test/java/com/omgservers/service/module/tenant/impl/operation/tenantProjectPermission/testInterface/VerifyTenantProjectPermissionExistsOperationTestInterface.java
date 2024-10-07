package com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.testInterface;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.VerifyTenantProjectPermissionExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VerifyTenantProjectPermissionExistsOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantProjectPermissionExistsOperation verifyTenantProjectPermissionExistsOperation;

    final PgPool pgPool;

    public Boolean execute(final Long tenantId,
                           final Long tenantProjectId,
                           final Long userId,
                           final TenantProjectPermissionQualifierEnum permission) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantProjectPermissionExistsOperation
                        .execute(sqlConnection, 0, tenantId, tenantProjectId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
