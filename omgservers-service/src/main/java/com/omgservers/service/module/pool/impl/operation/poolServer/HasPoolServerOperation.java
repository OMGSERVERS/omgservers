package com.omgservers.service.module.pool.impl.operation.poolServer;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasPoolServerOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long poolId,
                         Long id);
}
