package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.deletePoolRuntimeAssignment;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolRuntimeAssignmentOperation {
    Uni<Boolean> deletePoolRuntimeAssignment(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             Long poolId,
                                             Long id);
}
