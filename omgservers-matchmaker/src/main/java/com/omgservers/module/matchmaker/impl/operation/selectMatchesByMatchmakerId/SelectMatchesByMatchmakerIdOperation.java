package com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectMatchesByMatchmakerIdOperation {
    Uni<List<MatchModel>> selectMatchesByMatchmakerId(SqlConnection sqlConnection, int shard, Long matchmakerId);

    default List<MatchModel> selectMatchesByMatchmakerId(long timeout, PgPool pgPool, int shard, Long matchmakerId) {
        return pgPool.withTransaction(sqlConnection -> selectMatchesByMatchmakerId(sqlConnection, shard, matchmakerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
