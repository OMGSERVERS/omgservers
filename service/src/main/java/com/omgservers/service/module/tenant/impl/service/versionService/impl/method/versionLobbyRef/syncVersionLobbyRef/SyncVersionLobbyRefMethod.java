package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.syncVersionLobbyRef;

import com.omgservers.schema.module.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionLobbyRefMethod {
    Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(SyncVersionLobbyRefRequest request);
}
