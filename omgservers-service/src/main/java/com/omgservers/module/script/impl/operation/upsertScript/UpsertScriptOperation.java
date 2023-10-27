package com.omgservers.module.script.impl.operation.upsertScript;

import com.omgservers.model.script.ScriptModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertScriptOperation {
    Uni<Boolean> upsertScript(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              ScriptModel script);

    default Boolean upsertScript(long timeout,
                                 PgPool pgPool,
                                 int shard,
                                 ScriptModel script) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertScript(changeContext, sqlConnection, shard, script));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
