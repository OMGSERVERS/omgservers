package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewRequests;

import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRequestsMethod {
    Uni<ViewRequestsResponse> viewRequests(ViewRequestsRequest request);
}
