package com.omgservers.service.module.tenant.impl.operation.tenantPermission.deleteTenantPermission;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteTenantPermissionOperation {
    Uni<Boolean> deleteTenantPermission(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        Long tenantId,
                                        Long id);
}
