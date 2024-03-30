package com.omgservers.service.module.pool.impl.operation.pool.hasPool;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasPoolOperation {
    Uni<Boolean> hasPool(SqlConnection sqlConnection,
                         int shard,
                         Long id);
}
