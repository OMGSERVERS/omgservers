package com.omgservers.application.module.userModule.impl.operation.insertUserOperation;

import com.omgservers.application.module.userModule.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertUserOperation {
    Uni<Void> insertUser(SqlConnection sqlConnection,
                         int shard,
                         UserModel user);

    default void insertUser(long timeout, PgPool pgPool, int shard, UserModel user) {
        pgPool.withTransaction(sqlConnection -> insertUser(sqlConnection, shard, user))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
