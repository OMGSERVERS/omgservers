package com.omgservers.module.user.impl.operation.selectPlayerAttributes;

import com.omgservers.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectPlayerAttributesOperation {
    Uni<List<AttributeModel>> selectPlayerAttributes(SqlConnection sqlConnection,
                                                     int shard,
                                                     Long userId,
                                                     Long playerId);

    default List<AttributeModel> selectPlayerAttributes(long timeout, PgPool pgPool, int shard, Long userId, Long playerId) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerAttributes(sqlConnection, shard, userId, playerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
