package com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation;

import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectRuntimeOperation {
    Uni<RuntimeModel> selectRuntime(SqlConnection sqlConnection, int shard, UUID uuid);

    default RuntimeModel selectRuntime(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectRuntime(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
