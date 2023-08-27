package com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerOperation {
    Uni<Boolean> upsertMatchmaker(SqlConnection sqlConnection, int shard, MatchmakerModel matchmaker);

    default Boolean upsertMatchmaker(long timeout, PgPool pgPool, int shard, MatchmakerModel matchmaker) {
        return pgPool.withTransaction(sqlConnection -> upsertMatchmaker(sqlConnection, shard, matchmaker))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
