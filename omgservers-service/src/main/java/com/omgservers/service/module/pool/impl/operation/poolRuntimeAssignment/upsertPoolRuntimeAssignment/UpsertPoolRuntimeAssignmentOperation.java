package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.upsertPoolRuntimeAssignment;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolRuntimeAssignmentOperation {
    Uni<Boolean> upsertPoolRuntimeAssignmentRequest(ChangeContext<?> changeContext,
                                                    SqlConnection sqlConnection,
                                                    int shard,
                                                    PoolRuntimeAssignmentModel poolRuntimeAssignment);
}
