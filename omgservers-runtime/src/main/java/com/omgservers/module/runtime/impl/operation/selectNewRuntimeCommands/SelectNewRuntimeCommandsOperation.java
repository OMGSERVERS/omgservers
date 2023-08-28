package com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectNewRuntimeCommandsOperation {
    Uni<List<RuntimeCommandModel>> selectNewRuntimeCommands(SqlConnection sqlConnection, int shard, Long runtimeId);

    default List<RuntimeCommandModel> selectNewRuntimeCommands(long timeout, PgPool pgPool, int shard, Long runtimeId) {
        return pgPool.withTransaction(sqlConnection -> selectNewRuntimeCommands(sqlConnection, shard, runtimeId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
