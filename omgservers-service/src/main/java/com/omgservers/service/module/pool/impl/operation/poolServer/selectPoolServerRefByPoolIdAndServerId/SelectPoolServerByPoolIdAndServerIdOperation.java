package com.omgservers.service.module.pool.impl.operation.poolServer.selectPoolServerRefByPoolIdAndServerId;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerByPoolIdAndServerIdOperation {
    Uni<PoolServerRefModel> selectPoolServerRefByPoolIdAndServerId(SqlConnection sqlConnection,
                                                                   int shard,
                                                                   Long poolId,
                                                                   Long serverId);
}
