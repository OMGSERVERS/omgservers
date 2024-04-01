package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.upsertPoolRuntimeServerContainerRequest;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolRuntimeServerContainerRequestOperation {
    Uni<Boolean> upsertPoolRuntimeServerContainerRequest(ChangeContext<?> changeContext,
                                                         SqlConnection sqlConnection,
                                                         int shard,
                                                         PoolRuntimeServerContainerRequestModel poolRuntimeServerContainerRequest);
}
