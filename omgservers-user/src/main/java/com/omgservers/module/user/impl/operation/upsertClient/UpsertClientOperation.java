package com.omgservers.module.user.impl.operation.upsertClient;

import com.omgservers.ChangeContext;
import com.omgservers.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              final Long userId,
                              ClientModel client);

    default Boolean upsertClient(long timeout, PgPool pgPool, int shard, final Long userId, ClientModel client) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertClient(changeContext, sqlConnection, shard, userId, client));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
