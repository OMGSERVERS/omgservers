package com.omgservers.service.server.job.operation;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           Long id);
}
