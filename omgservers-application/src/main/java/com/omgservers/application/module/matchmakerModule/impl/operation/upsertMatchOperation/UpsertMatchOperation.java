package com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchOperation {
    Uni<Boolean> upsertMatch(SqlConnection sqlConnection, int shard, MatchModel match);

    default Boolean upsertMatch(long timeout, PgPool pgPool, int shard, MatchModel match) {
        return pgPool.withTransaction(sqlConnection -> upsertMatch(sqlConnection, shard, match))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
