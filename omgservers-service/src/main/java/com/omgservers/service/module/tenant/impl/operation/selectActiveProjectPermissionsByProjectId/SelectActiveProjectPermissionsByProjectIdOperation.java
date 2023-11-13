package com.omgservers.service.module.tenant.impl.operation.selectActiveProjectPermissionsByProjectId;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveProjectPermissionsByProjectIdOperation {
    Uni<List<ProjectPermissionModel>> selectActiveProjectPermissionsByProjectId(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long tenantId,
                                                                                Long projectId);
}
