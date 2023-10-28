package com.omgservers.module.system.impl.service.containerService.impl.method.syncContainer;

import com.omgservers.model.dto.internal.SyncContainerRequest;
import com.omgservers.model.dto.internal.SyncContainerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncContainerMethod {
    Uni<SyncContainerResponse> syncContainer(SyncContainerRequest request);
}
