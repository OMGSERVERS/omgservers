package com.omgservers.module.system.impl.operation.updateEventsRelayedFlagByIds;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface UpdateEventsRelayedFlagByIdsOperation {
    Uni<Boolean> updateEventsRelayedByIds(ChangeContext<?> changeContext,
                                          SqlConnection sqlConnection,
                                          List<Long> ids,
                                          Boolean relayed);

    default Boolean updateEventsRelayedByIds(long timeout,
                                             PgPool pgPool,
                                             List<Long> ids,
                                             Boolean relayed) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateEventsRelayedByIds(changeContext, sqlConnection, ids, relayed));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
