package com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeAssignmentsByRuntimeIdOperation {
    Uni<List<RuntimeAssignmentModel>> execute(SqlConnection sqlConnection,
                                              int slot,
                                              Long runtimeId);
}
