package com.omgservers.service.module.server.impl.service.serverService.impl.method.getServerContainer;

import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface GetServerContainerMethod {
    Uni<GetServerContainerResponse> getServerContainer(GetServerContainerRequest request);
}
