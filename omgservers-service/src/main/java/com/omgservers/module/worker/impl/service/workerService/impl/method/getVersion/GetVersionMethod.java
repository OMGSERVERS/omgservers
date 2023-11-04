package com.omgservers.module.worker.impl.service.workerService.impl.method.getVersion;

import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {
    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);
}
