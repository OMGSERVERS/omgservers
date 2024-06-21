package com.omgservers.service.module.system.impl.operation.job.upsertJob;

import com.omgservers.model.job.JobModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertJobOperation {
    Uni<Boolean> upsertJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           JobModel job);
}
