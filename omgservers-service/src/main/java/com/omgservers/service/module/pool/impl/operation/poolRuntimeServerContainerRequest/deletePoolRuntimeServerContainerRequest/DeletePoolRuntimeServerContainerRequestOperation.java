package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.deletePoolRuntimeServerContainerRequest;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolRuntimeServerContainerRequestOperation {
    Uni<Boolean> deletePoolRuntimeServerContainerRequest(ChangeContext<?> changeContext,
                                                         SqlConnection sqlConnection,
                                                         int shard,
                                                         Long poolId,
                                                         Long id);
}
