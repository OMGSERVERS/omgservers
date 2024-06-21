package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimePoolServerContainerRefOperation {
    Uni<Boolean> deleteRuntimePoolServerContainerRef(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     Long runtimeId,
                                                     Long id);
}
