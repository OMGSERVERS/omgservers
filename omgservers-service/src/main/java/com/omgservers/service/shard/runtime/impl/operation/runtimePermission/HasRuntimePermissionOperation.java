package com.omgservers.service.shard.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimePermissionOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long runtimeId,
                         Long userId,
                         RuntimePermissionEnum permission);
}
