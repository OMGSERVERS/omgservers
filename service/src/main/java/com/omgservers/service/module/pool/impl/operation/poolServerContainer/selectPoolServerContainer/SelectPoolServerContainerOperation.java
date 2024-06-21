package com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectPoolServerContainer;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerContainerOperation {
    Uni<PoolServerContainerModel> selectPoolServerContainer(SqlConnection sqlConnection,
                                                            int shard,
                                                            Long poolId,
                                                            Long serverId,
                                                            Long id);
}
