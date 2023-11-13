package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeGrants;

import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeGrantsMethod {
    Uni<ViewRuntimeGrantsResponse> viewRuntimeGrants(ViewRuntimeGrantsRequest request);
}
