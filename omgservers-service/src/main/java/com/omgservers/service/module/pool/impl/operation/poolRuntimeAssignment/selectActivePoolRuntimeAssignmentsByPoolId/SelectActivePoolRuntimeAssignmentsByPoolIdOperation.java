package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectActivePoolRuntimeAssignmentsByPoolId;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolRuntimeAssignmentsByPoolIdOperation {
    Uni<List<PoolRuntimeAssignmentModel>> selectActivePoolRuntimeAssignmentsByPoolId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId);
}
