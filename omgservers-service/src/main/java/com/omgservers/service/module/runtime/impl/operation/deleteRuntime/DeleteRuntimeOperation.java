package com.omgservers.service.module.runtime.impl.operation.deleteRuntime;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeOperation {
    Uni<Boolean> deleteRuntime(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long id);
}
