package com.omgservers.module.script.impl.operation.updateScriptSelf;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateScriptSelfOperation {
    Uni<Boolean> updateScriptSelf(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  Long id,
                                  String self);

    default Boolean updateScriptSelf(long timeout,
                                     PgPool pgPool,
                                     int shard,
                                     Long id,
                                     String self) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateScriptSelf(changeContext, sqlConnection, shard, id, self));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
