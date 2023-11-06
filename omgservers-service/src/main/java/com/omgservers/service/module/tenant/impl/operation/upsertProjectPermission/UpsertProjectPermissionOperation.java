package com.omgservers.service.module.tenant.impl.operation.upsertProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertProjectPermissionOperation {
    Uni<Boolean> upsertProjectPermission(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         ProjectPermissionModel permission);
}
