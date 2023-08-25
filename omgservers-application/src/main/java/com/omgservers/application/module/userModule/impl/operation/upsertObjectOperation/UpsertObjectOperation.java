package com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation;

import com.omgservers.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertObjectOperation {
    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, ObjectModel object);

    default Boolean upsertObject(long timeout, PgPool pgPool, int shard, ObjectModel object) {
        return pgPool.withTransaction(sqlConnection -> upsertObject(sqlConnection, shard, object))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
