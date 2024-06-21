package com.omgservers.service.module.tenant.impl.operation.tenantPermission.hasTenantPermission;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasTenantPermissionOperation {
    Uni<Boolean> hasTenantPermission(SqlConnection sqlConnection,
                                     int shard,
                                     Long tenantId,
                                     Long userId,
                                     TenantPermissionEnum permission);
}
