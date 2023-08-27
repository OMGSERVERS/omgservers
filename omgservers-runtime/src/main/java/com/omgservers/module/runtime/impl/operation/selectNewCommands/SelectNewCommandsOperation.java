package com.omgservers.module.runtime.impl.operation.selectNewCommands;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectNewCommandsOperation {
    Uni<List<RuntimeCommandModel>> selectNewCommands(SqlConnection sqlConnection, int shard, Long runtimeId);

    default List<RuntimeCommandModel> selectNewCommands(long timeout, PgPool pgPool, int shard, Long runtimeId) {
        return pgPool.withTransaction(sqlConnection -> selectNewCommands(sqlConnection, shard, runtimeId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
