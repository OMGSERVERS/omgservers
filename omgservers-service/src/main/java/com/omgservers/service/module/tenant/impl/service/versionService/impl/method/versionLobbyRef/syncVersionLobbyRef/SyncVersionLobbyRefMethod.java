package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.syncVersionLobbyRef;

import com.omgservers.model.dto.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionLobbyRefMethod {
    Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(SyncVersionLobbyRefRequest request);
}
