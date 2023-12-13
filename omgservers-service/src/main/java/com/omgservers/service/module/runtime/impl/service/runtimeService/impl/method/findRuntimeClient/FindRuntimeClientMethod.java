package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeClient;

import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeClientMethod {
    Uni<FindRuntimeClientResponse> findRuntimeClient(FindRuntimeClientRequest request);
}
