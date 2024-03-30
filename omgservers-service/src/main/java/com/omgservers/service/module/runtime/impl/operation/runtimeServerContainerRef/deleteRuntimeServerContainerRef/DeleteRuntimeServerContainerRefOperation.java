package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.deleteRuntimeServerContainerRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeServerContainerRefOperation {
    Uni<Boolean> deleteRuntimeServerContainerRef(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 Long runtimeId,
                                                 Long id);
}
