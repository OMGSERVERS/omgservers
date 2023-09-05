package com.omgservers.module.matchmaker.impl.operation.upsertMatch;

import com.omgservers.ChangeContext;
import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchOperation {
    Uni<Boolean> upsertMatch(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             MatchModel match);

    default Boolean upsertMatch(long timeout,
                                PgPool pgPool,
                                int shard,
                                MatchModel match) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertMatch(changeContext, sqlConnection, shard, match));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
