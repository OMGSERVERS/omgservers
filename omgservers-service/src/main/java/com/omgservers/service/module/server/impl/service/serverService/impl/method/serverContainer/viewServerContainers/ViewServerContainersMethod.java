package com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.viewServerContainers;

import com.omgservers.model.dto.server.ViewServerContainersRequest;
import com.omgservers.model.dto.server.ViewServerContainersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewServerContainersMethod {
    Uni<ViewServerContainersResponse> viewServerContainers(ViewServerContainersRequest request);
}
