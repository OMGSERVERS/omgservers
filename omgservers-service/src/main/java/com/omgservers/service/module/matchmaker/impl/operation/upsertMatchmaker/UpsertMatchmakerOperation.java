package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerOperation {
    Uni<Boolean> upsertMatchmaker(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  MatchmakerModel matchmaker);

    default Boolean upsertMatchmaker(long timeout,
                                     PgPool pgPool,
                                     int shard,
                                     MatchmakerModel matchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertMatchmaker(changeContext, sqlConnection, shard, matchmaker));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
