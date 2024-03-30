package com.omgservers.service.module.runtime.impl.operation.runtimePermission.upsertRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimePermissionOperation {
    Uni<Boolean> upsertRuntimePermission(final ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         RuntimePermissionModel permission);
}
