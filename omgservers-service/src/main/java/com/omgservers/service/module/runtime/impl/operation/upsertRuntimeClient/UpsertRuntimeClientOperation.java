package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeClient;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeClientOperation {
    Uni<Boolean> upsertRuntimeClient(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     RuntimeClientModel runtimeClient);
}
