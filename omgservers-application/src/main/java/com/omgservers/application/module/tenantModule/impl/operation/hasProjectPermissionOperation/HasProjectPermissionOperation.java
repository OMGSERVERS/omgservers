package com.omgservers.application.module.tenantModule.impl.operation.hasProjectPermissionOperation;

import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface HasProjectPermissionOperation {
    Uni<Boolean> hasProjectPermission(SqlConnection sqlConnection,
                                      int shard,
                                      UUID project,
                                      UUID user,
                                      ProjectPermissionEnum permission);

    default Boolean hasProjectPermission(long timeout,
                                         PgPool pgPool,
                                         int shard,
                                         UUID project,
                                         UUID user,
                                         ProjectPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> hasProjectPermission(sqlConnection, shard, project, user, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
