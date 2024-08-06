package com.omgservers.service.module.runtime.impl.operation.runtime.deleteRuntime;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeOperation {
    Uni<Boolean> deleteRuntime(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long id);
}
