package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePoolContainerRefMethod {
    Uni<FindRuntimePoolContainerRefResponse> execute(FindRuntimePoolContainerRefRequest request);
}
