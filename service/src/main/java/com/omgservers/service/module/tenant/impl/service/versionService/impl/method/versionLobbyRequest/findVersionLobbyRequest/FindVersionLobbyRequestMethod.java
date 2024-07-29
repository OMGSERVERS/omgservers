package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.findVersionLobbyRequest;

import com.omgservers.schema.module.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionLobbyRequestMethod {
    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(FindVersionLobbyRequestRequest request);
}
