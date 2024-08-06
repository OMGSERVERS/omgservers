package com.omgservers.service.server.service.job.operation.upsertJob;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertJobOperation {
    Uni<Boolean> upsertJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           JobModel job);
}
