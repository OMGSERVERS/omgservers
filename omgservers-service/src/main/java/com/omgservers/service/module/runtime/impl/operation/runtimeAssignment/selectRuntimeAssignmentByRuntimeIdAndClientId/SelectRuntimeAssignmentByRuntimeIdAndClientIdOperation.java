package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectRuntimeAssignmentByRuntimeIdAndClientId;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeAssignmentByRuntimeIdAndClientIdOperation {
    Uni<RuntimeAssignmentModel> selectRuntimeAssignmentByRuntimeIdAndEntityId(SqlConnection sqlConnection,
                                                                              int shard,
                                                                              Long runtimeId,
                                                                              Long clientId);
}
