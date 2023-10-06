package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.updateRuntimeCommandsStatus;

import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusRequest;
import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeCommandsStatusMethod {
    Uni<UpdateRuntimeCommandsStatusResponse> updateRuntimeCommandsStatus(UpdateRuntimeCommandsStatusRequest request);
}
