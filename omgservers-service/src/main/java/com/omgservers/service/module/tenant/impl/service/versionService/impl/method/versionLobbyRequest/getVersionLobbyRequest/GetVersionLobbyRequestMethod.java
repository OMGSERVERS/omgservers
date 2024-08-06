package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.getVersionLobbyRequest;

import com.omgservers.schema.module.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionLobbyRequestMethod {

    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(GetVersionLobbyRequestRequest request);
}
