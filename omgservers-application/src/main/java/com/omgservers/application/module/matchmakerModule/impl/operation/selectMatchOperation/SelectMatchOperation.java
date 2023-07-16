package com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectMatchOperation {
    Uni<MatchModel> selectMatch(SqlConnection sqlConnection, int shard, UUID uuid);

    default MatchModel selectMatch(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectMatch(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
