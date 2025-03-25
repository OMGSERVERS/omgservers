package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeStateMethod {

    Uni<UpdateRuntimeStateResponse> execute(UpdateRuntimeStateRequest request);
}
