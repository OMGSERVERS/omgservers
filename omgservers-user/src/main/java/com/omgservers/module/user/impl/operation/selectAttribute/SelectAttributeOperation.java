package com.omgservers.module.user.impl.operation.selectAttribute;

import com.omgservers.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectAttributeOperation {
    Uni<AttributeModel> selectAttribute(SqlConnection sqlConnection,
                                        int shard,
                                        final Long userId,
                                        Long playerId,
                                        String name);

    default AttributeModel selectAttribute(long timeout,
                                           PgPool pgPool,
                                           int shard,
                                           Long userId,
                                           Long playerId,
                                           String name) {
        return pgPool.withTransaction(sqlConnection -> selectAttribute(sqlConnection, shard, userId, playerId, name))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
