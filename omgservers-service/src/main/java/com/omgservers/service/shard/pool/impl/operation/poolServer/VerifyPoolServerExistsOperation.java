package com.omgservers.service.shard.pool.impl.operation.poolServer;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyPoolServerExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long poolId,
                         Long id);
}
