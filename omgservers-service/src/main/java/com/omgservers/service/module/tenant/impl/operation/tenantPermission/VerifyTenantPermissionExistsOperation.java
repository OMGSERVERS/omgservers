package com.omgservers.service.module.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantPermissionExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long userId,
                         TenantPermissionEnum tenantPermissionQualifier);
}
