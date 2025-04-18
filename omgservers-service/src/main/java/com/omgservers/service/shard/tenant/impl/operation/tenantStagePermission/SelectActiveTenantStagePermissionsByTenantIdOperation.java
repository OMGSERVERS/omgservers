package com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantStagePermissionsByTenantIdOperation {
    Uni<List<TenantStagePermissionModel>> execute(SqlConnection sqlConnection,
                                                  int slot,
                                                  Long tenantId);
}
