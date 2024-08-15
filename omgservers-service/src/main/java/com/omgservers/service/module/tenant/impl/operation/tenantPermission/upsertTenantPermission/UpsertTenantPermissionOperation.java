package com.omgservers.service.module.tenant.impl.operation.tenantPermission.upsertTenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantPermissionOperation {
    Uni<Boolean> upsertTenantPermission(final ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        TenantPermissionModel permission);
}
