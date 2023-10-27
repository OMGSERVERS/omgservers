package com.omgservers.module.tenant.impl.operation.upsertStagePermission;

import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertStagePermissionOperation {
    Uni<Boolean> upsertStagePermission(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       StagePermissionModel permission);

    default Boolean upsertStagePermission(long timeout,
                                          PgPool pgPool,
                                          int shard,
                                          StagePermissionModel permission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertStagePermission(changeContext, sqlConnection, shard, permission));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
