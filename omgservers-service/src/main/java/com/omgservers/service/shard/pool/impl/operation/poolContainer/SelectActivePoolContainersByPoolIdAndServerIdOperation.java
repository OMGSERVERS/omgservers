package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolContainersByPoolIdAndServerIdOperation {
    Uni<List<PoolContainerModel>> execute(SqlConnection sqlConnection,
                                          int shard,
                                          Long poolId,
                                          Long serverId);
}
