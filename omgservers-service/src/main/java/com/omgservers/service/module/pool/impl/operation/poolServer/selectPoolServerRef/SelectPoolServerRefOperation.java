package com.omgservers.service.module.pool.impl.operation.poolServer.selectPoolServerRef;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerRefOperation {
    Uni<PoolServerRefModel> selectPoolServerRef(SqlConnection sqlConnection,
                                                int shard,
                                                Long poolId,
                                                Long id);
}
