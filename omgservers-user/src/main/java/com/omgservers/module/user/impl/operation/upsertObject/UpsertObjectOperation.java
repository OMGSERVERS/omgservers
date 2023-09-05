package com.omgservers.module.user.impl.operation.upsertObject;

import com.omgservers.ChangeContext;
import com.omgservers.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertObjectOperation {
    Uni<Boolean> upsertObject(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long userId,
                              ObjectModel object);

    default Boolean upsertObject(long timeout, PgPool pgPool, int shard, Long userId, ObjectModel object) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertObject(changeContext, sqlConnection, shard, userId, object));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
