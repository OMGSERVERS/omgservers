package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.viewPoolRequests;

import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolRequestsMethod {
    Uni<ViewPoolRequestsResponse> viewPoolRequests(
            ViewPoolRequestsRequest request);
}
