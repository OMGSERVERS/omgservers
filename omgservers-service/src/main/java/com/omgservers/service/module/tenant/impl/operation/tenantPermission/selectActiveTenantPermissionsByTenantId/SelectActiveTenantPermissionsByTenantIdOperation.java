package com.omgservers.service.module.tenant.impl.operation.tenantPermission.selectActiveTenantPermissionsByTenantId;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantPermissionsByTenantIdOperation {
    Uni<List<TenantPermissionModel>> selectActiveTenantPermissionsByTenantId(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long tenantId);
}
