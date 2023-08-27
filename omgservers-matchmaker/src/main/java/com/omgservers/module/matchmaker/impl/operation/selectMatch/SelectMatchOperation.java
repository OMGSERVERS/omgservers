package com.omgservers.module.matchmaker.impl.operation.selectMatch;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectMatchOperation {
    Uni<MatchModel> selectMatch(SqlConnection sqlConnection, int shard, Long id);

    default MatchModel selectMatch(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatch(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
