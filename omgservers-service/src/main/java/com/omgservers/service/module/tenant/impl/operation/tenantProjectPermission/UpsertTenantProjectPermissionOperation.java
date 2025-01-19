package com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantProjectPermissionOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantProjectPermissionModel tenantProjectPermission);
}
