package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.deleteRuntimeAssignment;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeAssignmentOperation {
    Uni<Boolean> deleteRuntimeAssignment(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long runtimeId,
                                         Long id);
}
