package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DoRuntimeUpdateMethod {
    Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request);

    default DoRuntimeUpdateShardedResponse doRuntimeUpdate(long timeout, DoRuntimeUpdateShardedRequest request) {
        return doRuntimeUpdate(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
