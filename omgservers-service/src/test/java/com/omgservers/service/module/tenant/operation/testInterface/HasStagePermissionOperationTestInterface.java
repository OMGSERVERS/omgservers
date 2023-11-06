package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.service.module.tenant.impl.operation.hasStagePermission.HasStagePermissionOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HasStagePermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final HasStagePermissionOperation hasStagePermissionOperation;

    final PgPool pgPool;

    public Boolean hasStagePermission(final int shard,
                                       Long tenantId,
                                       Long stageId,
                                       Long userId,
                                       StagePermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> hasStagePermissionOperation
                        .hasStagePermission(sqlConnection, shard, tenantId, stageId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
