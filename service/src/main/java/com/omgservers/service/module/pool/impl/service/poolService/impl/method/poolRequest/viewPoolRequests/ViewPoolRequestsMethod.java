package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.viewPoolRequests;

import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolRequestsMethod {
    Uni<ViewPoolRequestsResponse> viewPoolRequests(
            ViewPoolRequestsRequest request);
}
