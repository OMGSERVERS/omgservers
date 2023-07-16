package com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertProjectOperation {
    Uni<Boolean> upsertProject(SqlConnection sqlConnection, int shard, ProjectModel project);

    default Boolean upsertProject(long timeout, PgPool pgPool, int shard, ProjectModel project) {
        return pgPool.withTransaction(sqlConnection -> upsertProject(sqlConnection, shard, project))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
