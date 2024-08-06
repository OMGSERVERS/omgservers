package com.omgservers.service.module.tenant.impl.operation.projectPermission.selectActiveProjectPermissionsByTenantId;

import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveProjectPermissionsByTenantIdOperation {
    Uni<List<ProjectPermissionModel>> selectActiveProjectPermissionsByTenantId(SqlConnection sqlConnection,
                                                                               int shard,
                                                                               Long tenantId);
}
