package com.omgservers.application.module.runtimeModule.impl.operation.upsertCommandOperation;

import com.omgservers.application.module.runtimeModule.model.command.CommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertCommandOperation {
    Uni<Boolean> upsertCommand(SqlConnection sqlConnection, int shard, CommandModel command);

    default Boolean upsertCommand(long timeout, PgPool pgPool, int shard, CommandModel command) {
        return pgPool.withTransaction(sqlConnection -> upsertCommand(sqlConnection, shard, command))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
