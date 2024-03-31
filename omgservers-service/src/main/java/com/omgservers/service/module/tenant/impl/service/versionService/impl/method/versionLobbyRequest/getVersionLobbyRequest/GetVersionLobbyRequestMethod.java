package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.getVersionLobbyRequest;

import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionLobbyRequestMethod {

    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(GetVersionLobbyRequestRequest request);
}
