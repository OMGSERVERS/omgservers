package com.omgservers.service.module.tenant.impl.operation.project.selectActiveProjectsByTenantId;

import com.omgservers.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveProjectsByTenantIdOperation {
    Uni<List<ProjectModel>> selectActiveProjectsByTenantId(SqlConnection sqlConnection,
                                                           int shard,
                                                           Long tenantId);
}
