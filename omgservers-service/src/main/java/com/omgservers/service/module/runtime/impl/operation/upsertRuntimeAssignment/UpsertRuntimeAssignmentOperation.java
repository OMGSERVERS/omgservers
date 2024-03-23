package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeAssignment;

import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeAssignmentOperation {
    Uni<Boolean> upsertRuntimeAssignment(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         RuntimeAssignmentModel runtimeAssignment);
}
