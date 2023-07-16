package com.omgservers.application.module.userModule.impl.operation.selectTokenOperation;

import com.omgservers.application.module.userModule.model.token.TokenModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectTokenOperation {
    Uni<TokenModel> selectToken(SqlConnection sqlConnection, int shard, UUID tokenUuid);

    default TokenModel selectToken(long timeout, PgPool pgPool, int shard, UUID tokenUuid) {
        return pgPool.withTransaction(sqlConnection -> selectToken(sqlConnection, shard, tokenUuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
