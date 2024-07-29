package com.omgservers.service.module.pool.impl.operation.poolServer.selectActivePoolServersByPoolId;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolServersByPoolIdOperation {
    Uni<List<PoolServerModel>> selectActivePoolServersByPoolId(SqlConnection sqlConnection,
                                                               int shard,
                                                               Long poolId);
}
