package com.omgservers.service.shard.tenant.operation.tenantStagePermission.testInterface;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.VerifyTenantStagePermissionExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VerifyTenantStagePermissionExistsOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantStagePermissionExistsOperation verifyTenantStagePermissionExistsOperation;

    final PgPool pgPool;

    public Boolean execute(final Long tenantId,
                           final Long stageId,
                           final Long userId,
                           final TenantStagePermissionQualifierEnum permission) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantStagePermissionExistsOperation
                        .execute(sqlConnection, 0, tenantId, stageId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
