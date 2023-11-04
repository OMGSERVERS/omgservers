package com.omgservers.service.module.tenant.impl.operation.hasProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface HasProjectPermissionOperation {
    Uni<Boolean> hasProjectPermission(SqlConnection sqlConnection,
                                      int shard,
                                      Long tenantId,
                                      Long projectId,
                                      Long userId,
                                      ProjectPermissionEnum permission);

    default Boolean hasProjectPermission(long timeout,
                                         PgPool pgPool,
                                         int shard,
                                         Long tenantId,
                                         Long projectId,
                                         Long userId,
                                         ProjectPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection ->
                        hasProjectPermission(sqlConnection, shard, tenantId, projectId, userId, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
