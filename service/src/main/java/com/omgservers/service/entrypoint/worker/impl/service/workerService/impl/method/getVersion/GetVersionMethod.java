package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.getVersion;

import com.omgservers.schema.entrypoint.worker.GetVersionWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetVersionWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {
    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);
}
