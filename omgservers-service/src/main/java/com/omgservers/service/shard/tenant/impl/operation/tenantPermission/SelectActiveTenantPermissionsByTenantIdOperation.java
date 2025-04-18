package com.omgservers.service.shard.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantPermissionsByTenantIdOperation {
    Uni<List<TenantPermissionModel>> execute(SqlConnection sqlConnection,
                                             int slot,
                                             Long tenantId);
}
