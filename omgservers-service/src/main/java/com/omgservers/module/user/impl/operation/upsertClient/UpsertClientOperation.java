package com.omgservers.module.user.impl.operation.upsertClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              ClientModel client);

    default Boolean upsertClient(long timeout, PgPool pgPool, int shard, ClientModel client) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertClient(changeContext, sqlConnection, shard, client));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
