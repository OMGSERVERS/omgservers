package com.omgservers.service.module.pool.impl.operation.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRequestByPoolIdAndRuntimeIdOperation {
    Uni<PoolRequestModel> execute(SqlConnection sqlConnection,
                                  int shard,
                                  Long poolId,
                                  Long runtimeId);
}
