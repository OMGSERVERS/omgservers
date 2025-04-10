package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolContainerOperation {
    Uni<PoolContainerModel> execute(SqlConnection sqlConnection,
                                    int shard,
                                    Long poolId,
                                    Long id);
}
