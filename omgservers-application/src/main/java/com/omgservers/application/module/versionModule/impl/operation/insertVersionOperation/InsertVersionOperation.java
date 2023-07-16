package com.omgservers.application.module.versionModule.impl.operation.insertVersionOperation;

import com.omgservers.application.module.versionModule.model.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertVersionOperation {
    Uni<Void> insertVersion(SqlConnection sqlConnection,
                            int shard,
                            VersionModel version);

    default void insertVersion(long timeout, PgPool pgPool, int shard, VersionModel version) {
        pgPool.withTransaction(sqlConnection -> insertVersion(sqlConnection, shard, version))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
