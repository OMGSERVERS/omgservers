package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.findVersionLobbyRef;

import com.omgservers.schema.module.tenant.FindVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionLobbyRefMethod {
    Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(FindVersionLobbyRefRequest request);
}
