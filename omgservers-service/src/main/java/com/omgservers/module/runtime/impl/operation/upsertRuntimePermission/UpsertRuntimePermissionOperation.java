package com.omgservers.module.runtime.impl.operation.upsertRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimePermissionOperation {
    Uni<Boolean> upsertRuntimePermission(final ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         RuntimePermissionModel permission);
}
