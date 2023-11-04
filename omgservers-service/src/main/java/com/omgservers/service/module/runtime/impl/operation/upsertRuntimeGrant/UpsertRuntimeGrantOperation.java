package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertRuntimeGrantOperation {
    Uni<Boolean> upsertRuntimeGrant(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    RuntimeGrantModel runtimeGrant);

    default Boolean upsertRuntimeGrant(long timeout,
                                       PgPool pgPool,
                                       int shard,
                                       RuntimeGrantModel runtimeGrant) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertRuntimeGrant(changeContext, sqlConnection, shard, runtimeGrant));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
