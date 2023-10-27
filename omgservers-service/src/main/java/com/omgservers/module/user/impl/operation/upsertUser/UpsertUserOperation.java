package com.omgservers.module.user.impl.operation.upsertUser;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertUserOperation {
    Uni<Boolean> upsertUser(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            UserModel user);

    default Boolean upsertUser(long timeout, PgPool pgPool, int shard, UserModel user) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertUser(changeContext, sqlConnection, shard, user));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
