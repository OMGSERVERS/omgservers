package com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertMatchOperation {
    Uni<Void> insertMatch(SqlConnection sqlConnection, int shard, MatchModel match);

    default void insertMatch(long timeout, PgPool pgPool, int shard, MatchModel match) {
        pgPool.withTransaction(sqlConnection -> insertMatch(sqlConnection, shard, match))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
