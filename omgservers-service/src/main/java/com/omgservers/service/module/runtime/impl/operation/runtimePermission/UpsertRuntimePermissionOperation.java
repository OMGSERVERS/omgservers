package com.omgservers.service.module.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimePermissionOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         RuntimePermissionModel permission);
}
