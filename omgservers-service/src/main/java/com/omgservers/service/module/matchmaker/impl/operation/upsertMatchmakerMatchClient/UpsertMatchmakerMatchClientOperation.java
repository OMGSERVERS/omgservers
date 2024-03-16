package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchClient;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerMatchClientOperation {
    Uni<Boolean> upsertMatchmakerMatchClient(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             MatchmakerMatchClientModel matchmakerMatchClient);

    default Boolean upsertMatchmakerMatchClient(long timeout,
                                                PgPool pgPool,
                                                int shard,
                                                MatchmakerMatchClientModel matchClient) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertMatchmakerMatchClient(changeContext, sqlConnection, shard, matchClient));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
