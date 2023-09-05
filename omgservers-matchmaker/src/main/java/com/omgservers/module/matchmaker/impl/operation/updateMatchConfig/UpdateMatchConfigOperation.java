package com.omgservers.module.matchmaker.impl.operation.updateMatchConfig;

import com.omgservers.ChangeContext;
import com.omgservers.model.match.MatchConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateMatchConfigOperation {
    Uni<Boolean> updateMatch(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long matchmakerId,
                             Long matchId,
                             MatchConfigModel config);

    default Boolean updateMatch(long timeout,
                                PgPool pgPool,
                                int shard,
                                Long matchmakerId,
                                Long matchId,
                                MatchConfigModel config) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateMatch(changeContext, sqlConnection, shard, matchmakerId, matchId, config));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
