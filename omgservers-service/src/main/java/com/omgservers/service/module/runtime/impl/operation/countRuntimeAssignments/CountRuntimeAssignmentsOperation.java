package com.omgservers.service.module.runtime.impl.operation.countRuntimeAssignments;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface CountRuntimeAssignmentsOperation {
    Uni<Integer> countRuntimeAssignments(SqlConnection sqlConnection,
                                         int shard,
                                         Long runtimeId);
}
