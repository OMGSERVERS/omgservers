package com.omgservers.service.module.tenant.impl.operation.project.selectProject;

import com.omgservers.schema.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectProjectOperation {
    Uni<ProjectModel> selectProject(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long id);
}
