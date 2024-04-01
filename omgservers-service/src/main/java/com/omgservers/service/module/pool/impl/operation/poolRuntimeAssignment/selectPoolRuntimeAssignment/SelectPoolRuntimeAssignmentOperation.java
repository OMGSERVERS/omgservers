package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignment;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeAssignmentOperation {
    Uni<PoolRuntimeAssignmentModel> selectPoolRuntimeAssignment(SqlConnection sqlConnection,
                                                                int shard,
                                                                Long poolId,
                                                                Long id);
}
