package com.omgservers.service.module.pool.impl.operation.poolServer.hasPoolServer;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasPoolServerOperation {
    Uni<Boolean> hasPoolServer(SqlConnection sqlConnection,
                               int shard,
                               Long poolId,
                               Long id);
}
