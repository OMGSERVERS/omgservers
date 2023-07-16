package com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation;

import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEntity;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertStagePermissionOperation {
    Uni<Boolean> upsertStagePermission(SqlConnection sqlConnection, int shard, StagePermissionEntity permission);

    default Boolean upsertStagePermission(long timeout, PgPool pgPool, int shard, StagePermissionEntity permission) {
        return pgPool.withTransaction(sqlConnection -> upsertStagePermission(sqlConnection, shard, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
