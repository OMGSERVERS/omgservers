package com.omgservers.application.module.tenantModule.impl.operation.insertProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertProjectOperation {
    Uni<Void> insertProject(SqlConnection sqlConnection, int shard, ProjectModel project);

    default void insertProject(long timeout, PgPool pgPool, int shard, ProjectModel project) {
        pgPool.withTransaction(sqlConnection -> insertProject(sqlConnection, shard, project))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
