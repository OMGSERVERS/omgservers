package com.omgservers.service.module.pool.impl.operation.poolRequest.selectActivePoolRequestsByPoolId;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolRequestsByPoolIdOperation {
    Uni<List<PoolRequestModel>> selectActivePoolRequestsByPoolId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId);
}
