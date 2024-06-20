package com.omgservers.service.module.system.impl.operation.job.selectJobByEntityId;

import com.omgservers.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectJobByEntityIdOperation {
    Uni<JobModel> selectJobByEntityId(SqlConnection sqlConnection,
                                      Long entityId);
}
