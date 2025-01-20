package com.omgservers.service.shard.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimePermissionsByRuntimeIdOperation {
    Uni<List<RuntimePermissionModel>> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long runtimeId);
}
