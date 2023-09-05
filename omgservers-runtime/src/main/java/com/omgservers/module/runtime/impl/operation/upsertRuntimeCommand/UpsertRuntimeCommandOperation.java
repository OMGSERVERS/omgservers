package com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertRuntimeCommandOperation {
    Uni<Boolean> upsertRuntimeCommand(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      RuntimeCommandModel runtimeCommand);

    default Boolean upsertRuntimeCommand(long timeout,
                                         PgPool pgPool,
                                         int shard,
                                         RuntimeCommandModel runtimeCommand) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertRuntimeCommand(changeContext, sqlConnection, shard, runtimeCommand));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
