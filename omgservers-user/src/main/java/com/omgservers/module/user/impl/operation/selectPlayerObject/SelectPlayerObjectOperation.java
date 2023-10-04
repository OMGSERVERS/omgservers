package com.omgservers.module.user.impl.operation.selectPlayerObject;

import com.omgservers.model.player.PlayerObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectPlayerObjectOperation {
    Uni<PlayerObjectModel> selectPlayerObject(SqlConnection sqlConnection,
                                              int shard,
                                              Long userId,
                                              Long playerId);

    default PlayerObjectModel selectPlayerObject(long timeout, PgPool pgPool, int shard, Long userId, Long playerId) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerObject(sqlConnection, shard, userId, playerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
