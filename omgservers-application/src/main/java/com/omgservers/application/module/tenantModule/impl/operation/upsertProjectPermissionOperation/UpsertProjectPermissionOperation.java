package com.omgservers.application.module.tenantModule.impl.operation.upsertProjectPermissionOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertProjectPermissionOperation {
    Uni<Boolean> upsertProjectPermission(SqlConnection sqlConnection, int shard, ProjectPermissionModel permission);

    default Boolean upsertProjectPermission(long timeout, PgPool pgPool, int shard, ProjectPermissionModel permission) {
        return pgPool.withTransaction(sqlConnection -> upsertProjectPermission(sqlConnection, shard, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
