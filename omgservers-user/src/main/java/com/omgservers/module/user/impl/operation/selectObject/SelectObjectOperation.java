package com.omgservers.module.user.impl.operation.selectObject;

import com.omgservers.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectObjectOperation {
    Uni<ObjectModel> selectObject(SqlConnection sqlConnection,
                                  int shard,
                                  Long userId,
                                  Long playerId,
                                  String name);

    default ObjectModel selectObject(long timeout,
                                     PgPool pgPool,
                                     int shard,
                                     Long userId,
                                     Long playerId,
                                     String name) {
        return pgPool.withTransaction(sqlConnection -> selectObject(sqlConnection, shard, userId, playerId, name))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
