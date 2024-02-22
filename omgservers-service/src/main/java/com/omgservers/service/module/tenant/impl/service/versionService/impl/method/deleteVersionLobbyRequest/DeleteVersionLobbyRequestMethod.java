package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionLobbyRequest;

import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionLobbyRequestMethod {
    Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(DeleteVersionLobbyRequestRequest request);
}
