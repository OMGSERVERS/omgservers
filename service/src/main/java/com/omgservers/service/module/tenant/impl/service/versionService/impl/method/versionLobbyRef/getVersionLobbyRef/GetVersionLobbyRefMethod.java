package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.getVersionLobbyRef;

import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionLobbyRefMethod {

    Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(GetVersionLobbyRefRequest request);
}
