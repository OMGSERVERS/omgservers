package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeClient;

import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeClientMethod {
    Uni<DeleteRuntimeClientResponse> deleteRuntimeClient(DeleteRuntimeClientRequest request);
}
