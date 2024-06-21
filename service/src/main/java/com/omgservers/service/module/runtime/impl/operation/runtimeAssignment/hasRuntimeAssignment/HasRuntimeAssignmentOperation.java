package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.hasRuntimeAssignment;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimeAssignmentOperation {
    Uni<Boolean> hasRuntimeAssignment(SqlConnection sqlConnection,
                                      int shard,
                                      Long runtimeId,
                                      Long clientId);
}
