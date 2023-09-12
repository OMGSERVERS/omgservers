package com.omgservers.module.tenant.impl.operation.hasStagePermission;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface HasStagePermissionOperation {
    Uni<Boolean> hasStagePermission(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long stageId,
                                    Long userId,
                                    StagePermissionEnum permission);

    default Boolean hasStagePermission(long timeout,
                                       PgPool pgPool,
                                       int shard,
                                       Long tenantId,
                                       Long stageId,
                                       Long userId,
                                       StagePermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection ->
                        hasStagePermission(sqlConnection, shard, tenantId, stageId, userId, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
