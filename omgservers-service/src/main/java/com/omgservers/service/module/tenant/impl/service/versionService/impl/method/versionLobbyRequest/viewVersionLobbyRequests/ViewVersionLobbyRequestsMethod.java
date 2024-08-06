package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.viewVersionLobbyRequests;

import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionLobbyRequestsMethod {
    Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(ViewVersionLobbyRequestsRequest request);
}
