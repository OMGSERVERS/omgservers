package com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchmakerOperation;

import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectMatchmakerOperation {
    Uni<MatchmakerModel> selectMatchmaker(SqlConnection sqlConnection, int shard, Long id);

    default MatchmakerModel selectMatchmaker(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatchmaker(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
