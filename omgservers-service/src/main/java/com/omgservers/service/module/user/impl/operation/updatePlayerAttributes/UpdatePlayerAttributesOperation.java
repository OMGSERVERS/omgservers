package com.omgservers.service.module.user.impl.operation.updatePlayerAttributes;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdatePlayerAttributesOperation {
    Uni<Boolean> updatePlayerAttributes(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        Long userId,
                                        Long playerId,
                                        PlayerAttributesModel attributes);

    default Boolean updatePlayerAttributes(long timeout,
                                           PgPool pgPool,
                                           int shard,
                                           Long userId,
                                           Long playerId,
                                           PlayerAttributesModel attributes) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updatePlayerAttributes(changeContext, sqlConnection, shard, userId, playerId, attributes));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
