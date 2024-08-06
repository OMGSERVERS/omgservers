package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.interchangeMethod;

import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeWorkerResponse> interchange(InterchangeWorkerRequest request);
}
