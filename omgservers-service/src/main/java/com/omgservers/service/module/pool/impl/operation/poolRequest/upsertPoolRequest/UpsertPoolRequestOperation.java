package com.omgservers.service.module.pool.impl.operation.poolRequest.upsertPoolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolRequestOperation {
    Uni<Boolean> upsertPoolRequest(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   PoolRequestModel poolRequest);
}
