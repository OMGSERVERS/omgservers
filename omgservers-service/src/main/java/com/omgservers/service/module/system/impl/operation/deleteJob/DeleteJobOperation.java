package com.omgservers.service.module.system.impl.operation.deleteJob;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           Long id);
}
