package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.deleteVersionLobbyRequest;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionLobbyRequestMethod {
    Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(DeleteVersionLobbyRequestRequest request);
}
