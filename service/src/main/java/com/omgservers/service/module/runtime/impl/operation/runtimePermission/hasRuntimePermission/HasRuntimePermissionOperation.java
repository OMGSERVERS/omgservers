package com.omgservers.service.module.runtime.impl.operation.runtimePermission.hasRuntimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimePermissionOperation {
    Uni<Boolean> hasTenantPermission(SqlConnection sqlConnection,
                                     int shard,
                                     Long runtimeId,
                                     Long userId,
                                     RuntimePermissionEnum permission);
}
