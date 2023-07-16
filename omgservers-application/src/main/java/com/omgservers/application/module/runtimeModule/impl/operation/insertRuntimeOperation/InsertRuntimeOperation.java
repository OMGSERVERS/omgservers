package com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation;

import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertRuntimeOperation {
    Uni<Void> insertRuntime(SqlConnection sqlConnection, int shard, RuntimeModel runtime);

    default void insertRuntime(long timeout, PgPool pgPool, int shard, RuntimeModel runtime) {
        pgPool.withTransaction(sqlConnection -> insertRuntime(sqlConnection, shard, runtime))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
