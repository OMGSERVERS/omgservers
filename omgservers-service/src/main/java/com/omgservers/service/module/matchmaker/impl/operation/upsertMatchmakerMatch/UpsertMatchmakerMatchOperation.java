package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatch;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerMatchOperation {
    Uni<Boolean> upsertMatchmakerMatch(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       MatchmakerMatchModel matchmakerMatch);

    default Boolean upsertMatchmakerMatch(long timeout,
                                          PgPool pgPool,
                                          int shard,
                                          MatchmakerMatchModel match) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertMatchmakerMatch(changeContext, sqlConnection, shard, match));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
