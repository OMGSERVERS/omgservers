package com.omgservers.application.module.userModule.impl.operation.selectPlayerAttributesOperation;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public interface SelectPlayerAttributesOperation {
    Uni<List<AttributeModel>> selectPlayerAttributes(SqlConnection sqlConnection,
                                                     int shard,
                                                     UUID player);

    default List<AttributeModel> selectPlayerAttributes(long timeout, PgPool pgPool, int shard, UUID player) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerAttributes(sqlConnection, shard, player))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
