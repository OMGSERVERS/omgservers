package com.omgservers.module.runtime.impl.operation.hasRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface HasRuntimeGrantOperation {
    Uni<Boolean> hasRuntimeGrant(SqlConnection sqlConnection,
                                 int shard,
                                 Long runtimeId,
                                 Long entityId,
                                 RuntimeGrantPermissionEnum permission);

    default Boolean hasRuntimeGrant(long timeout,
                                    PgPool pgPool,
                                    int shard,
                                    Long runtimeId,
                                    Long entityId,
                                    RuntimeGrantPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection ->
                        hasRuntimeGrant(sqlConnection, shard, runtimeId, entityId, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
