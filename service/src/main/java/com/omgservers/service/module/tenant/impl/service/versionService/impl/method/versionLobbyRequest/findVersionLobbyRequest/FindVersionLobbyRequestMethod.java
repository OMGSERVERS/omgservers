package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.findVersionLobbyRequest;

import com.omgservers.model.dto.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionLobbyRequestMethod {
    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(FindVersionLobbyRequestRequest request);
}
