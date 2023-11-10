package com.omgservers.service.module.worker.impl.service.workerService.impl.method.getWorkerContext;

import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface GetWorkerContextMethod {
    Uni<GetWorkerContextWorkerResponse> getWorkerContext(GetWorkerContextWorkerRequest request);
}
