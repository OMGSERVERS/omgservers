package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerRequestsMethod {
    Uni<ViewMatchmakerRequestsResponse> execute(ViewMatchmakerRequestsRequest request);
}
