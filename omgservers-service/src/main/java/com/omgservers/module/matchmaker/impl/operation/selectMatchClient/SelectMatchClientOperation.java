package com.omgservers.module.matchmaker.impl.operation.selectMatchClient;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectMatchClientOperation {
    Uni<MatchClientModel> selectMatchClient(SqlConnection sqlConnection, int shard, Long matchmakerId, Long id);

    default MatchClientModel selectMatchClient(long timeout, PgPool pgPool, int shard, Long matchmakerId, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatchClient(sqlConnection, shard, matchmakerId, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
