package com.omgservers.service.shard.pool.impl.operation.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerOperation {
    Uni<PoolServerModel> execute(SqlConnection sqlConnection,
                                 int shard,
                                 Long poolId,
                                 Long id);
}
