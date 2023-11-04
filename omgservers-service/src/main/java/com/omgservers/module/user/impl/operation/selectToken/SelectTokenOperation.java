package com.omgservers.module.user.impl.operation.selectToken;

import com.omgservers.model.token.TokenModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectTokenOperation {
    Uni<TokenModel> selectToken(SqlConnection sqlConnection, int shard, Long tokenId);

    default TokenModel selectToken(long timeout, PgPool pgPool, int shard, Long tokenId) {
        return pgPool.withTransaction(sqlConnection -> selectToken(sqlConnection, shard, tokenId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
