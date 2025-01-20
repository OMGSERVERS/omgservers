package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolRequestsByPoolIdOperation {
    Uni<List<PoolRequestModel>> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long poolId);
}
