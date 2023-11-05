package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersions;

import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionsMethod {
    Uni<ViewVersionsResponse> viewVersions(ViewVersionsRequest request);
}
