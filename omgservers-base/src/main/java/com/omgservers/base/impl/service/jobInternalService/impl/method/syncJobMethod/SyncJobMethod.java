package com.omgservers.base.impl.service.jobInternalService.impl.method.syncJobMethod;

import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);
}
