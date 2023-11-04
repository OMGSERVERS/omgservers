package com.omgservers.service.module.user.impl.operation.selectPlayerAttributes;

import com.omgservers.model.player.PlayerAttributesModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectPlayerAttributesOperation {
    Uni<PlayerAttributesModel> selectPlayerAttributes(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long userId,
                                                      Long playerId);

    default PlayerAttributesModel selectPlayerAttributes(long timeout,
                                                         PgPool pgPool,
                                                         int shard,
                                                         Long userId,
                                                         Long playerId) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerAttributes(sqlConnection, shard, userId, playerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
