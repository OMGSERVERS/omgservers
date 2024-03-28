package com.omgservers.service.module.pool.impl.operation.upsertPoolServerRef;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolServerRefOperation {
    Uni<Boolean> upsertPoolServerRef(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     PoolServerRefModel poolServerRef);
}
