package com.omgservers.service.module.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantStagePermissionExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long tenantStageId,
                         Long userId,
                         TenantStagePermissionEnum tenantStagePermissionQualifier);
}
