package com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimePoolContainerRefOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         RuntimePoolContainerRefModel runtimePoolContainerRef);
}
