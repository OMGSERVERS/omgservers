package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectPoolRuntimeServerContainerRef;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeServerContainerRefOperation {
    Uni<PoolRuntimeServerContainerRefModel> selectPoolRuntimeServerContainerRef(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long poolId,
                                                                                Long id);
}
