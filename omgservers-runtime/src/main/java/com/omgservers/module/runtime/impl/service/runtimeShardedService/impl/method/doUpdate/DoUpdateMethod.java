package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doUpdate;

import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DoUpdateMethod {
    Uni<Void> doUpdate(DoUpdateShardedRequest request);
}
