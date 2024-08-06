package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.createToken;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);
}
