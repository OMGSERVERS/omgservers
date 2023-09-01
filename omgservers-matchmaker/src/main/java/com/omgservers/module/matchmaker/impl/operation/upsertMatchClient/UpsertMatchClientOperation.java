package com.omgservers.module.matchmaker.impl.operation.upsertMatchClient;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchClientOperation {
    Uni<Boolean> upsertMatchClient(SqlConnection sqlConnection, int shard, MatchClientModel matchClient);

    default Boolean upsertMatchClient(long timeout, PgPool pgPool, int shard, MatchClientModel matchClient) {
        return pgPool.withTransaction(sqlConnection -> upsertMatchClient(sqlConnection, shard, matchClient))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
