package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRequestByPoolIdAndRuntimeIdOperation {
    Uni<PoolRequestModel> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long poolId,
                                  Long runtimeId);
}
