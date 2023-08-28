package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DoRuntimeUpdateMethod {
    Uni<Void> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request);
}
