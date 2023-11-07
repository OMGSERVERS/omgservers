package com.omgservers.service.module.worker.impl.service.workerService.impl.method.getRuntimeState;

import com.omgservers.model.dto.worker.GetRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeStateMethod {
    Uni<GetRuntimeStateWorkerResponse> getRuntimeState(GetRuntimeStateWorkerRequest request);
}
