package com.omgservers.module.user.impl.operation.upsertToken;

import com.omgservers.model.token.TokenModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTokenOperation {
    Uni<Boolean> upsertToken(SqlConnection sqlConnection, int shard, TokenModel token);

    default Boolean upsertToken(long timeout, PgPool pgPool, int shard, TokenModel token) {
        return pgPool.withTransaction(sqlConnection -> upsertToken(sqlConnection, shard, token))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
