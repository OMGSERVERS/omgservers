package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakerRequests;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakerRequestsMethod {
    Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            ViewVersionMatchmakerRequestsRequest request);
}
