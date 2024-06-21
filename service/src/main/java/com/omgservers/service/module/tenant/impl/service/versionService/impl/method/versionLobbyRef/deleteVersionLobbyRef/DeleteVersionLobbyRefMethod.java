package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.deleteVersionLobbyRef;

import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionLobbyRefMethod {
    Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(DeleteVersionLobbyRefRequest request);
}
