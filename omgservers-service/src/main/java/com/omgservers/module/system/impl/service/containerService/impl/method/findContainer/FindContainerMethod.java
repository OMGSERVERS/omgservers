package com.omgservers.module.system.impl.service.containerService.impl.method.findContainer;

import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import io.smallrye.mutiny.Uni;

public interface FindContainerMethod {
    Uni<FindContainerResponse> findContainer(FindContainerRequest request);
}
