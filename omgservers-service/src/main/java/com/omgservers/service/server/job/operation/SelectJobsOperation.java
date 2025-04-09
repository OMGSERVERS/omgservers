package com.omgservers.service.server.job.operation;

import com.omgservers.schema.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectJobsOperation {
    Uni<List<JobModel>> selectJobs(SqlConnection sqlConnection);
}
