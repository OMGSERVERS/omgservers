package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.getVersionLobbyRef;

import com.omgservers.model.dto.tenant.GetVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionLobbyRefMethod {

    Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(GetVersionLobbyRefRequest request);
}
