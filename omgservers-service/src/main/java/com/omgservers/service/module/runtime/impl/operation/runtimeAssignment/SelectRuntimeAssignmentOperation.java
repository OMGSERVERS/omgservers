package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeAssignmentOperation {
    Uni<RuntimeAssignmentModel> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long runtimeId,
                                        Long id);
}
