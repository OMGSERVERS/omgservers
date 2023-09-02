package com.omgservers.module.matchmaker.impl.operation.updateMatch;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateMatchOperation {
    Uni<Void> updateMatch(SqlConnection sqlConnection, int shard, MatchModel match);

    default void updateMatch(long timeout, PgPool pgPool, int shard, MatchModel match) {
        pgPool.withTransaction(sqlConnection -> updateMatch(sqlConnection, shard, match))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
