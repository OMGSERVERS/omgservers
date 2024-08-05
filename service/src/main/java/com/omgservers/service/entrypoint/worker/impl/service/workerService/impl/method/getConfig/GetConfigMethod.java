package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.getConfig;

import com.omgservers.schema.entrypoint.worker.GetConfigWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface GetConfigMethod {
    Uni<GetConfigWorkerResponse> getConfig(GetConfigWorkerRequest request);
}
