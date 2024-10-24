package com.omgservers.service.module.pool.impl.operation.pool;

import com.omgservers.schema.model.pool.PoolModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolOperation {
    Uni<PoolModel> execute(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
