package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.updateRuntimeAssignmentLastActivity;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateRuntimeAssignmentLastActivityOperation {
    Uni<Boolean> updateRuntimeAssignmentLastActivity(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     final Long runtimeId,
                                                     final Long clientId);
}
