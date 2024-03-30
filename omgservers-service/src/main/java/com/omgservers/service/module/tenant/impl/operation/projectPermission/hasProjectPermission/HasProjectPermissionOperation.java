package com.omgservers.service.module.tenant.impl.operation.projectPermission.hasProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasProjectPermissionOperation {
    Uni<Boolean> hasProjectPermission(SqlConnection sqlConnection,
                                      int shard,
                                      Long tenantId,
                                      Long projectId,
                                      Long userId,
                                      ProjectPermissionEnum permission);
}
