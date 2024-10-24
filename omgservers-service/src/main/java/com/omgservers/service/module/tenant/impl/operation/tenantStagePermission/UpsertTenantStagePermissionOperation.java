package com.omgservers.service.module.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantStagePermissionOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantStagePermissionModel tenantStagePermission);
}
