package com.omgservers.service.module.worker.impl.service.workerService.impl.method.interchangeMethod;

import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeWorkerResponse> interchange(InterchangeWorkerRequest request);
}
