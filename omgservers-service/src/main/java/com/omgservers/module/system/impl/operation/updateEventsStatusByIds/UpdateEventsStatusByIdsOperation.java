package com.omgservers.module.system.impl.operation.updateEventsStatusByIds;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface UpdateEventsStatusByIdsOperation {
    Uni<Boolean> updateEventsStatusByIds(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         List<Long> ids,
                                         EventStatusEnum status);

    default Boolean updateEventsStatusByIds(long timeout,
                                            PgPool pgPool,
                                            List<Long> ids,
                                            EventStatusEnum status) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateEventsStatusByIds(changeContext, sqlConnection, ids, status));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
