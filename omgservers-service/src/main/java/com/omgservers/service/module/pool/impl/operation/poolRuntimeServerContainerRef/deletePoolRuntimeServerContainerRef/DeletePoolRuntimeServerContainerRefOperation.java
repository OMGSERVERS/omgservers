package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.deletePoolRuntimeServerContainerRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolRuntimeServerContainerRefOperation {
    Uni<Boolean> deletePoolRuntimeServerContainerRef(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     Long poolId,
                                                     Long id);
}
