package com.omgservers.module.runtime.impl.operation.hasRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface HasRuntimeGrantOperation {
    Uni<Boolean> hasRuntimeGrant(SqlConnection sqlConnection,
                                 int shard,
                                 Long runtimeId,
                                 Long shardKey,
                                 Long entityId,
                                 RuntimeGrantTypeEnum type);

    default Boolean hasRuntimeGrant(long timeout,
                                    PgPool pgPool,
                                    int shard,
                                    Long runtimeId,
                                    Long shardKey,
                                    Long entityId,
                                    RuntimeGrantTypeEnum type) {
        return pgPool.withTransaction(sqlConnection ->
                        hasRuntimeGrant(sqlConnection, shard, runtimeId, shardKey, entityId, type))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
