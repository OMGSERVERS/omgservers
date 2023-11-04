package com.omgservers.service.module.worker.impl.service.workerService.impl.method.createToken;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);
}
