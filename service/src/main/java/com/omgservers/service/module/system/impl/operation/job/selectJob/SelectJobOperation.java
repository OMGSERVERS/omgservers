package com.omgservers.service.module.system.impl.operation.job.selectJob;

import com.omgservers.schema.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectJobOperation {
    Uni<JobModel> selectJob(SqlConnection sqlConnection, Long id);
}
