package com.omgservers.service.module.system.impl.operation.selectJobs;

import com.omgservers.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectJobsOperation {
    Uni<List<JobModel>> selectJobs(SqlConnection sqlConnection);
}
