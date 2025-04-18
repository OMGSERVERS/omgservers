package com.omgservers.service.shard.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantPermissionOperation {
    Uni<Boolean> execute(final ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         TenantPermissionModel permission);
}
