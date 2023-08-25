package com.omgservers.application.module.userModule.impl.operation.selectPlayerAttributesOperation;

import com.omgservers.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectPlayerAttributesOperation {
    Uni<List<AttributeModel>> selectPlayerAttributes(SqlConnection sqlConnection,
                                                     int shard,
                                                     Long playerId);

    default List<AttributeModel> selectPlayerAttributes(long timeout, PgPool pgPool, int shard, Long playerId) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerAttributes(sqlConnection, shard, playerId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
