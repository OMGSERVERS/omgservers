package com.omgservers.service.shard.pool.impl.operation.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolServersByPoolIdOperation {
    Uni<List<PoolServerModel>> execute(SqlConnection sqlConnection,
                                       int shard,
                                       Long poolId);
}
