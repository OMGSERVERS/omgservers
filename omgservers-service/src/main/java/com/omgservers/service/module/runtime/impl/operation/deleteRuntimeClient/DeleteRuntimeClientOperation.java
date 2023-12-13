package com.omgservers.service.module.runtime.impl.operation.deleteRuntimeClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeClientOperation {
    Uni<Boolean> deleteRuntimeClient(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     Long runtimeId,
                                     Long id);
}
