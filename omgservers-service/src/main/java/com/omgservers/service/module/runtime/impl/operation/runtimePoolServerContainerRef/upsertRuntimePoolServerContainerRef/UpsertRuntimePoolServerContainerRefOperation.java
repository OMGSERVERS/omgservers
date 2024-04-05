package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.upsertRuntimePoolServerContainerRef;

import com.omgservers.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimePoolServerContainerRefOperation {
    Uni<Boolean> upsertRuntimePoolServerContainerRef(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     RuntimePoolServerContainerRefModel runtimePoolServerContainerRef);
}
