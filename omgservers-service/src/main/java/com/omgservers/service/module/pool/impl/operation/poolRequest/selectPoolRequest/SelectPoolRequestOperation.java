package com.omgservers.service.module.pool.impl.operation.poolRequest.selectPoolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRequestOperation {
    Uni<PoolRequestModel> selectPoolRequest(SqlConnection sqlConnection,
                                            int shard,
                                            Long poolId,
                                            Long id);
}
