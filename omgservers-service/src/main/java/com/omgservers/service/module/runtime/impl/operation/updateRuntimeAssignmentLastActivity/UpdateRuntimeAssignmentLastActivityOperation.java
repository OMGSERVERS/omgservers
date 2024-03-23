package com.omgservers.service.module.runtime.impl.operation.updateRuntimeAssignmentLastActivity;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateRuntimeAssignmentLastActivityOperation {
    Uni<Boolean> updateRuntimeAssignmentLastActivity(ChangeContext<?> changeContext,
                                                     SqlConnection sqlConnection,
                                                     int shard,
                                                     final Long runtimeId,
                                                     final Long clientId);
}
