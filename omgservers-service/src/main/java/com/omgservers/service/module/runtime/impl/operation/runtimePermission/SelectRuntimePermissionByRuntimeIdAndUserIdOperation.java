package com.omgservers.service.module.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimePermissionByRuntimeIdAndUserIdOperation {
    Uni<RuntimePermissionModel> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long runtimeId,
                                        Long userId,
                                        RuntimePermissionEnum permission);
}
