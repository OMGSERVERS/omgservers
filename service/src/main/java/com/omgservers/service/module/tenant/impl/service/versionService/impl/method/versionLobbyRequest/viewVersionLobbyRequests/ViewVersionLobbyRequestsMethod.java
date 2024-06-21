package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.viewVersionLobbyRequests;

import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionLobbyRequestsMethod {
    Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(ViewVersionLobbyRequestsRequest request);
}
