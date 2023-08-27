package com.omgservers.module.runtime.impl.operation.selectRuntime;

import com.omgservers.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectRuntimeOperation {
    Uni<RuntimeModel> selectRuntime(SqlConnection sqlConnection, int shard, Long id);

    default RuntimeModel selectRuntime(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectRuntime(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
