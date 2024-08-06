package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.viewVersions;

import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionsMethod {
    Uni<ViewVersionsResponse> viewVersions(ViewVersionsRequest request);
}
