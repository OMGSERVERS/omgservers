package com.omgservers.module.system.impl.service.containerService.impl.method.deleteContainer;

import com.omgservers.model.dto.internal.DeleteContainerRequest;
import com.omgservers.model.dto.internal.DeleteContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteContainerMethod {
    Uni<DeleteContainerResponse> deleteContainer(DeleteContainerRequest request);
}
