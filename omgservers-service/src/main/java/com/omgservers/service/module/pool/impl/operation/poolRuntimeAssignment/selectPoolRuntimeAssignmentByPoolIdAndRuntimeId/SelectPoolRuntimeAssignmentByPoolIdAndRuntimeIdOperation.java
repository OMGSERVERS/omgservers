package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignmentByPoolIdAndRuntimeId;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation {
    Uni<PoolRuntimeAssignmentModel> selectPoolRuntimeAssignmentByPoolIdAndRuntimeId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long runtimeId);
}
