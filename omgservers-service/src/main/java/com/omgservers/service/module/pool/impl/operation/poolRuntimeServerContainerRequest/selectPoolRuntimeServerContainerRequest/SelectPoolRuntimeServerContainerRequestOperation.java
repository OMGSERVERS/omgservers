package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectPoolRuntimeServerContainerRequest;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeServerContainerRequestOperation {
    Uni<PoolRuntimeServerContainerRequestModel> selectPoolRuntimeServerContainerRequest(SqlConnection sqlConnection,
                                                                                        int shard,
                                                                                        Long poolId,
                                                                                        Long id);
}
