package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.upsertPoolRuntimeServerContainerRef;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolRuntimeServerContainerRefOperation {
    Uni<Boolean> upsertPoolRuntimeServerContainerRef(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     PoolRuntimeServerContainerRefModel poolRuntimeServerContainerRef);
}
