package com.omgservers.service.module.runtime.impl.operation.selectRuntimePermissionByRuntimeIdAndUserId;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimePermissionByRuntimeIdAndUserIdOperation {
    Uni<RuntimePermissionModel> selectRuntimePermissionByRuntimeIdAndUserId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long runtimeId,
                                                                            Long userId,
                                                                            RuntimePermissionEnum permission,
                                                                            Boolean deleted);
}
