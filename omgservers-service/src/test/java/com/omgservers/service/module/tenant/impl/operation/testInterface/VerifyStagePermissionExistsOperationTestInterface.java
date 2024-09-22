package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.VerifyTenantStagePermissionExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VerifyStagePermissionExistsOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantStagePermissionExistsOperation verifyTenantStagePermissionExistsOperation;

    final PgPool pgPool;

    public Boolean execute(final int shard,
                           final Long tenantId,
                           final Long stageId,
                           final Long userId,
                           final TenantStagePermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantStagePermissionExistsOperation
                        .execute(sqlConnection, shard, tenantId, stageId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
