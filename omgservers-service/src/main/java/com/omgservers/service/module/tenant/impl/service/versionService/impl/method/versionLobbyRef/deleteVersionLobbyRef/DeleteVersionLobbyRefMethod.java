package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.deleteVersionLobbyRef;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionLobbyRefMethod {
    Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(DeleteVersionLobbyRefRequest request);
}
