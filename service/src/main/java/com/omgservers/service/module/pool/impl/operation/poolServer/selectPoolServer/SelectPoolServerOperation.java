package com.omgservers.service.module.pool.impl.operation.poolServer.selectPoolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerOperation {
    Uni<PoolServerModel> selectPoolServer(SqlConnection sqlConnection,
                                          int shard,
                                          Long poolId,
                                          Long id);
}
