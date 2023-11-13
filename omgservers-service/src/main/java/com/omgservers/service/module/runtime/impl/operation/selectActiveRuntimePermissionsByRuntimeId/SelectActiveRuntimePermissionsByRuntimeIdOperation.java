package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimePermissionsByRuntimeId;

import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimePermissionsByRuntimeIdOperation {
    Uni<List<RuntimePermissionModel>> selectActiveRuntimePermissionsByRuntimeId(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long runtimeId);
}
