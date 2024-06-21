package com.omgservers.service.module.tenant.impl.operation.projectPermission.deleteProjectPermission;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteProjectPermissionOperation {
    Uni<Boolean> deleteProjectPermission(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long id);
}
