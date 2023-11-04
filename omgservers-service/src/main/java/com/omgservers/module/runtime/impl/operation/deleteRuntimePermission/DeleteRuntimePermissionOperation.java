package com.omgservers.module.runtime.impl.operation.deleteRuntimePermission;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimePermissionOperation {
    Uni<Boolean> deleteRuntimePermission(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long runtimeId,
                                         Long id);
}
