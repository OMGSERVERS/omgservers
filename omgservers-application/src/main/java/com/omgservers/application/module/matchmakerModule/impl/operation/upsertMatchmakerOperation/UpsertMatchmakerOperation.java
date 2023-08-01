package com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation;

import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerOperation {
    Uni<Boolean> upsertMatchmaker(SqlConnection sqlConnection, int shard, MatchmakerModel matchmaker);

    default Boolean upsertMatchmaker(long timeout, PgPool pgPool, int shard, MatchmakerModel matchmaker) {
        return pgPool.withTransaction(sqlConnection -> upsertMatchmaker(sqlConnection, shard, matchmaker))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
