package com.omgservers.service.module.pool.impl.operation.poolServerRef.deletePoolServerRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolServerRefOperation {
    Uni<Boolean> deletePoolServerRef(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     Long poolId,
                                     Long id);
}
