package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectRuntimeAssignment;

import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeAssignmentOperation {
    Uni<RuntimeAssignmentModel> selectRuntimeAssignment(SqlConnection sqlConnection,
                                                        int shard,
                                                        Long runtimeId,
                                                        Long id);
}
