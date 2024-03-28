package com.omgservers.service.module.server.impl.service.serverService.impl.method.deleteServerContainer;

import com.omgservers.model.dto.server.DeleteServerContainerRequest;
import com.omgservers.model.dto.server.DeleteServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteServerContainerMethod {
    Uni<DeleteServerContainerResponse> deleteServerContainer(DeleteServerContainerRequest request);
}
