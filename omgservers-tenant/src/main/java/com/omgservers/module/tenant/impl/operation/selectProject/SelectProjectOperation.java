package com.omgservers.module.tenant.impl.operation.selectProject;

import com.omgservers.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectProjectOperation {
    Uni<ProjectModel> selectProject(SqlConnection sqlConnection, int shard, Long id);

    default ProjectModel selectProject(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectProject(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
