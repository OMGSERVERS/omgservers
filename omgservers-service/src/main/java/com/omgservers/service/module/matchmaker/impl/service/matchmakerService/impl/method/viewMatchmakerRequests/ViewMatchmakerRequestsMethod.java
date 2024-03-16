package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerRequests;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerRequestsMethod {
    Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(ViewMatchmakerRequestsRequest request);
}
