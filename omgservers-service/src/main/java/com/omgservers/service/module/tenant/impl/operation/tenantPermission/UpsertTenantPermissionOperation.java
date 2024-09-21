package com.omgservers.service.module.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantPermissionOperation {
    Uni<Boolean> execute(final ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantPermissionModel permission);
}
