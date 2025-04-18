package com.omgservers.service.shard.pool.impl.operation.pool;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         PoolModel pool);
}
