package com.omgservers.service.module.pool.impl.operation.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolServerOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         PoolServerModel poolServerRef);
}
