package com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantProjectPermissionsByTenantIdOperation {
    Uni<List<TenantProjectPermissionModel>> execute(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long tenantId);
}
