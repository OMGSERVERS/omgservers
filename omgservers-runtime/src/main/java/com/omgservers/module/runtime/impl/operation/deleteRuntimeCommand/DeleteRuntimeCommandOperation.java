package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteRuntimeCommandOperation {
    Uni<Boolean> deleteRuntimeCommand(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      Long runtimeId,
                                      Long id);

    default Boolean deleteRuntimeCommand(long timeout,
                                         PgPool pgPool,
                                         int shard,
                                         Long runtimeId,
                                         Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteRuntimeCommand(changeContext, sqlConnection, shard, runtimeId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
