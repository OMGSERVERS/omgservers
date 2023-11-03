package com.omgservers.module.system.impl.service.containerService.impl.method.getContainer;

import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import io.smallrye.mutiny.Uni;

public interface GetContainerMethod {
    Uni<GetContainerResponse> getContainer(GetContainerRequest request);
}
