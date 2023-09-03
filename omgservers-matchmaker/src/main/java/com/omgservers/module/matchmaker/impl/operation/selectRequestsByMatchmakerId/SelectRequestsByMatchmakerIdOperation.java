package com.omgservers.module.matchmaker.impl.operation.selectRequestsByMatchmakerId;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectRequestsByMatchmakerIdOperation {
    Uni<List<RequestModel>> selectRequestsByMatchmakerId(SqlConnection sqlConnection, int shard, Long matchmakerId);

    default List<RequestModel> selectRequestsByMatchmakerId(long timeout, PgPool pgPool, int shard, Long matchmakerId) {
        return pgPool.withTransaction(sqlConnection -> selectRequestsByMatchmakerId(sqlConnection, shard, matchmakerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
