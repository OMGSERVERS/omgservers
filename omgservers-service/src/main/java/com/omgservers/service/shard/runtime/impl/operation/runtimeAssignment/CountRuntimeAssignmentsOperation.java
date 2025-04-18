package com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface CountRuntimeAssignmentsOperation {
    Uni<Integer> execute(SqlConnection sqlConnection,
                         int slot,
                         Long runtimeId);
}
