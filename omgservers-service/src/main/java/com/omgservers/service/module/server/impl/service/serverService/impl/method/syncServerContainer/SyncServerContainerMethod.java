package com.omgservers.service.module.server.impl.service.serverService.impl.method.syncServerContainer;

import com.omgservers.model.dto.server.SyncServerContainerRequest;
import com.omgservers.model.dto.server.SyncServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncServerContainerMethod {
    Uni<SyncServerContainerResponse> syncServerContainer(SyncServerContainerRequest request);
}
