package com.omgservers.application.module.userModule.impl.operation.upsertUserOperation;

import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertUserOperation {
    Uni<Boolean> upsertUser(SqlConnection sqlConnection,
                            int shard,
                            UserModel user);

    default Boolean upsertUser(long timeout, PgPool pgPool, int shard, UserModel user) {
        return pgPool.withTransaction(sqlConnection -> upsertUser(sqlConnection, shard, user))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
