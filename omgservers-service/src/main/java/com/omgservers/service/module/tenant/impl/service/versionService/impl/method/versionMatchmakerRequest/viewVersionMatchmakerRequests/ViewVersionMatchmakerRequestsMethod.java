package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.viewVersionMatchmakerRequests;

import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakerRequestsMethod {
    Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            ViewVersionMatchmakerRequestsRequest request);
}
