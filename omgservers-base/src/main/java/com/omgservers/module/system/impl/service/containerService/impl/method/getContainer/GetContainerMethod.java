package com.omgservers.module.system.impl.service.containerService.impl.method.getContainer;

import com.omgservers.dto.internal.GetContainerRequest;
import com.omgservers.dto.internal.GetContainerResponse;
import io.smallrye.mutiny.Uni;

public interface GetContainerMethod {
    Uni<GetContainerResponse> getContainer(GetContainerRequest request);
}
