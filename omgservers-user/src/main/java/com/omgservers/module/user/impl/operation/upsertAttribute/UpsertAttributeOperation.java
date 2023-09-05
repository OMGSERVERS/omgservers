package com.omgservers.module.user.impl.operation.upsertAttribute;

import com.omgservers.ChangeContext;
import com.omgservers.model.attribute.AttributeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertAttributeOperation {
    Uni<Boolean> upsertAttribute(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 int shard,
                                 AttributeModel attribute);

    default Boolean upsertAttribute(long timeout, PgPool pgPool, int shard, AttributeModel attribute) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertAttribute(changeContext, sqlConnection, shard, attribute));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
