package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeAssignmentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         RuntimeAssignmentModel runtimeAssignment);
}
