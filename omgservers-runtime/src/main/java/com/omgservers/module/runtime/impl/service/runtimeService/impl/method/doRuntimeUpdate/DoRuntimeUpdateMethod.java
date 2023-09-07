package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DoRuntimeUpdateMethod {
    Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(DoRuntimeUpdateRequest request);

    default DoRuntimeUpdateResponse doRuntimeUpdate(long timeout, DoRuntimeUpdateRequest request) {
        return doRuntimeUpdate(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
