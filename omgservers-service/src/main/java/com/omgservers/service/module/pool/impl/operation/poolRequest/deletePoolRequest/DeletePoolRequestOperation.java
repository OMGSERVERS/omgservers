package com.omgservers.service.module.pool.impl.operation.poolRequest.deletePoolRequest;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolRequestOperation {
    Uni<Boolean> deletePoolRequest(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long poolId,
                                   Long id);
}
