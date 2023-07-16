package com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation;

import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertMatchmakerOperation {
    Uni<Void> insertMatchmaker(SqlConnection sqlConnection, int shard, MatchmakerModel matchmaker);

    default void insertMatchmaker(long timeout, PgPool pgPool, int shard, MatchmakerModel matchmaker) {
        pgPool.withTransaction(sqlConnection -> insertMatchmaker(sqlConnection, shard, matchmaker))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
