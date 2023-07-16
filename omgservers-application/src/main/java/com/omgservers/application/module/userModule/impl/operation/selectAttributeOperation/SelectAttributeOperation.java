package com.omgservers.application.module.userModule.impl.operation.selectAttributeOperation;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectAttributeOperation {
    Uni<AttributeModel> selectAttribute(SqlConnection sqlConnection,
                                        int shard,
                                        UUID player,
                                        String name);

    default AttributeModel selectAttribute(long timeout, PgPool pgPool, int shard, UUID player, String name) {
        return pgPool.withTransaction(sqlConnection -> selectAttribute(sqlConnection, shard, player, name))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
