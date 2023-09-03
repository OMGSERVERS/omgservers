package com.omgservers.module.tenant.impl.operation.upsertStagePermission;

import com.omgservers.ChangeContext;
import com.omgservers.model.stagePermission.StagePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertStagePermissionOperation {
    Uni<Boolean> upsertStagePermission(ChangeContext changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long tenantId,
                                       StagePermissionModel permission);

    default Boolean upsertStagePermission(long timeout,
                                          PgPool pgPool,
                                          int shard,
                                          Long tenantId,
                                          StagePermissionModel permission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertStagePermission(changeContext, sqlConnection, shard, tenantId, permission));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
