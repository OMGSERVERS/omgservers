package com.omgservers.module.user.impl.operation.upsertToken;

import com.omgservers.ChangeContext;
import com.omgservers.model.token.TokenModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTokenOperation {
    Uni<Boolean> upsertToken(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             TokenModel token);

    default Boolean upsertToken(long timeout, PgPool pgPool, int shard, TokenModel token) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertToken(changeContext, sqlConnection, shard, token));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
