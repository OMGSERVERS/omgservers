package com.omgservers.module.system.impl.service.jobService.impl.method.syncJob;

import com.omgservers.model.dto.internal.SyncJobRequest;
import com.omgservers.model.dto.internal.SyncJobResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);
}
