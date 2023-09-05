package com.omgservers.module.matchmaker.impl.operation.deleteMatchClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteMatchClientOperation {
    Uni<Boolean> deleteMatchClient(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long matchmakerId,
                                   Long id);

    default Boolean deleteMatchClient(long timeout,
                                      PgPool pgPool,
                                      int shard,
                                      Long matchmakerId,
                                      Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteMatchClient(changeContext, sqlConnection, shard, matchmakerId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
