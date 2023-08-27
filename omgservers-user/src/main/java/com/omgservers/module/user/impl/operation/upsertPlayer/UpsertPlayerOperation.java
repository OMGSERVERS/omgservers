package com.omgservers.module.user.impl.operation.upsertPlayer;

import com.omgservers.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertPlayerOperation {
    Uni<Boolean> upsertPlayer(SqlConnection sqlConnection, int shard, PlayerModel player);

    default Boolean upsertPlayer(long timeout, PgPool pgPool, int shard, PlayerModel player) {
        return pgPool.withTransaction(sqlConnection -> upsertPlayer(sqlConnection, shard, player))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
