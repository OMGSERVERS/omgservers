package com.omgservers.module.matchmaker.impl.operation.upsertMatchClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchClientOperation {
    Uni<Boolean> upsertMatchClient(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   MatchClientModel matchClient);

    default Boolean upsertMatchClient(long timeout,
                                      PgPool pgPool,
                                      int shard,
                                      MatchClientModel matchClient) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertMatchClient(changeContext, sqlConnection, shard, matchClient));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
