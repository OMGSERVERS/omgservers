package com.omgservers.service.module.pool.impl.operation.selectPool;

import com.omgservers.model.pool.PoolModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolOperation {
    Uni<PoolModel> selectPool(SqlConnection sqlConnection,
                              int shard,
                              Long id);
}