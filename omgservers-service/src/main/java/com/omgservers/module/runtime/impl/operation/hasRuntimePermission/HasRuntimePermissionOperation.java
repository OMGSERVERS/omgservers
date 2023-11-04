package com.omgservers.module.runtime.impl.operation.hasRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimePermissionOperation {
    Uni<Boolean> hasTenantPermission(SqlConnection sqlConnection,
                                     int shard,
                                     Long runtimeId,
                                     Long userId,
                                     RuntimePermissionEnum permission);
}
