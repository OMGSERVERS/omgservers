package com.omgservers.module.tenant.impl.service.versionService.impl.method.viewVersionRuntimes;

import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionRuntimesMethod {
    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(ViewVersionRuntimesRequest request);
}
