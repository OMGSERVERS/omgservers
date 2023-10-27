package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.findRuntimeGrant;

import com.omgservers.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.dto.runtime.FindRuntimeGrantResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeGrantMethod {
    Uni<FindRuntimeGrantResponse> findRuntimeGrant(FindRuntimeGrantRequest request);
}
