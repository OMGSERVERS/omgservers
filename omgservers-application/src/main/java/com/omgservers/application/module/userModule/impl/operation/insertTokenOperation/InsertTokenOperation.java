package com.omgservers.application.module.userModule.impl.operation.insertTokenOperation;

import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserTokenContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertTokenOperation {
    Uni<UserTokenContainerModel> insertToken(SqlConnection sqlConnection, int shard, UserModel userModel);

    default UserTokenContainerModel insertToken(long timeout, PgPool pgPool, int shard, UserModel user) {
        return pgPool.withTransaction(sqlConnection -> insertToken(sqlConnection, shard, user))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
