package com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantProjectPermissionExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long tenantProjectId,
                         Long userId,
                         TenantProjectPermissionEnum tenantProjectPermissionQualifier);
}
