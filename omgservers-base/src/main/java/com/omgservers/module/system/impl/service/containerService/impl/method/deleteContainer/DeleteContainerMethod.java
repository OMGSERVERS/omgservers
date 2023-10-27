package com.omgservers.module.system.impl.service.containerService.impl.method.deleteContainer;

import com.omgservers.dto.internal.DeleteContainerRequest;
import com.omgservers.dto.internal.DeleteContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteContainerMethod {
    Uni<DeleteContainerResponse> deleteContainer(DeleteContainerRequest request);
}
