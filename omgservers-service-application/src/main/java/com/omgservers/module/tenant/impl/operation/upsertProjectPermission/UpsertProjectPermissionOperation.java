package com.omgservers.module.tenant.impl.operation.upsertProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertProjectPermissionOperation {
    Uni<Boolean> upsertProjectPermission(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         ProjectPermissionModel permission);

    default Boolean upsertProjectPermission(long timeout,
                                            PgPool pgPool,
                                            int shard,
                                            ProjectPermissionModel permission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertProjectPermission(changeContext, sqlConnection, shard, permission));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
