package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectActiveRuntimeAssignmentsByRuntimeId;

import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeAssignmentsByRuntimeIdOperation {
    Uni<List<RuntimeAssignmentModel>> selectActiveRuntimeAssignmentsByRuntimeId(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long runtimeId);
}
