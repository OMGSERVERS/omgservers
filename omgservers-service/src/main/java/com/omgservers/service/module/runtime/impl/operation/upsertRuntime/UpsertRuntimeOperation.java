package com.omgservers.service.module.runtime.impl.operation.upsertRuntime;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertRuntimeOperation {
    Uni<Boolean> upsertRuntime(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               RuntimeModel runtime);

    default Boolean upsertRuntime(long timeout, PgPool pgPool, int shard, RuntimeModel runtime) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertRuntime(changeContext, sqlConnection, shard, runtime));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
