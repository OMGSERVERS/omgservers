package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.syncJobMethod;

import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);
}
