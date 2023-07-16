package com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertAttributeOperation {
    Uni<Boolean> upsertAttribute(SqlConnection sqlConnection, int shard, AttributeModel attribute);

    default Boolean upsertAttribute(long timeout, PgPool pgPool, int shard, AttributeModel attribute) {
        return pgPool.withTransaction(sqlConnection -> upsertAttribute(sqlConnection, shard, attribute))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
