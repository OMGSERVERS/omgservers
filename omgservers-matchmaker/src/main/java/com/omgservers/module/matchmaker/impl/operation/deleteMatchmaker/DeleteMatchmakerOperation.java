package com.omgservers.module.matchmaker.impl.operation.deleteMatchmaker;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteMatchmakerOperation {
    Uni<Boolean> deleteMatchmaker(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  Long id);

    default Boolean deleteMatchmaker(long timeout,
                                     PgPool pgPool,
                                     int shard,
                                     Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteMatchmaker(changeContext, sqlConnection, shard, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
