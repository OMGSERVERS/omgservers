package com.omgservers.service.module.tenant.impl.operation.selectProject;

import com.omgservers.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectProjectOperation {
    Uni<ProjectModel> selectProject(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long id,
                                    Boolean deleted);
}
