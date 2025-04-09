package com.omgservers.service.server.job.operation;

import com.omgservers.schema.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectJobByEntityIdOperation {
    Uni<JobModel> selectJobByEntityId(SqlConnection sqlConnection,
                                      Long shardKey,
                                      Long entityId);
}
