package com.omgservers.application.module.tenantModule.impl.operation.deleteProjectOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteProjectOperation {
    Uni<Boolean> deleteProject(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteProject(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteProject(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
