package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeAssignmentsMethod {
    Uni<ViewRuntimeAssignmentsResponse> execute(ShardModel shardModel, ViewRuntimeAssignmentsRequest request);
}
