package com.omgservers.service.shard.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantPermissionExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long tenantId,
                         Long userId,
                         TenantPermissionQualifierEnum tenantPermissionQualifier);
}
