package com.omgservers.service.module.server.impl.service.serverService.impl.method.server.syncServer;

import com.omgservers.model.dto.server.SyncServerRequest;
import com.omgservers.model.dto.server.SyncServerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncServerMethod {
    Uni<SyncServerResponse> syncServer(SyncServerRequest request);
}
