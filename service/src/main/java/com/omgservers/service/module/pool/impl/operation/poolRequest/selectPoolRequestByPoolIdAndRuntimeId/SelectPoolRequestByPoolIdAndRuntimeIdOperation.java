package com.omgservers.service.module.pool.impl.operation.poolRequest.selectPoolRequestByPoolIdAndRuntimeId;

import com.omgservers.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRequestByPoolIdAndRuntimeIdOperation {
    Uni<PoolRequestModel> selectPoolRequestByPoolIdAndRuntimeId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long runtimeId);
}
