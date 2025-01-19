package com.omgservers.service.service.job.operation.upsertJob;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertJobOperation {
    Uni<Boolean> upsertJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           JobModel job);
}
