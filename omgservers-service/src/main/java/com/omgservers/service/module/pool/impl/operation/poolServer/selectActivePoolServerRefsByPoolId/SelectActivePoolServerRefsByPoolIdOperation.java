package com.omgservers.service.module.pool.impl.operation.poolServer.selectActivePoolServerRefsByPoolId;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolServerRefsByPoolIdOperation {
    Uni<List<PoolServerRefModel>> selectActivePoolServerRefsByPoolId(SqlConnection sqlConnection,
                                                                     int shard,
                                                                     Long poolId);
}
