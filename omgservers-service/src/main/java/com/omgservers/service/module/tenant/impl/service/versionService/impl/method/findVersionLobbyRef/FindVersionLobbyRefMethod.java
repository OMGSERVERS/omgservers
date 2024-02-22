package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionLobbyRef;

import com.omgservers.model.dto.tenant.FindVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionLobbyRefMethod {
    Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(FindVersionLobbyRefRequest request);
}
