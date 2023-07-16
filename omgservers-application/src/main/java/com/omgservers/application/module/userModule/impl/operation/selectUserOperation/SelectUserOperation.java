package com.omgservers.application.module.userModule.impl.operation.selectUserOperation;

import com.omgservers.application.module.userModule.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectUserOperation {
    Uni<UserModel> selectUser(SqlConnection sqlConnection,
                              int shard,
                              UUID uuid);

    default UserModel selectUser(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectUser(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
