package com.omgservers.service.module.runtime.impl.operation.runtimePermission.deleteRuntimePermission;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimePermissionOperation {
    Uni<Boolean> deleteRuntimePermission(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long runtimeId,
                                         Long id);
}
