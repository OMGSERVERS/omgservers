package com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectProjectOperation {
    Uni<ProjectModel> selectProject(SqlConnection sqlConnection, int shard, UUID uuid);

    default ProjectModel selectProject(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectProject(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
