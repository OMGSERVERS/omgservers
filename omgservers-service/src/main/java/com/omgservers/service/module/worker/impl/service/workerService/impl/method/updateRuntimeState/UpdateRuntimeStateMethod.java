package com.omgservers.service.module.worker.impl.service.workerService.impl.method.updateRuntimeState;

import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeStateMethod {
    Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(UpdateRuntimeStateWorkerRequest request);
}
