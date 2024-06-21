package com.omgservers.service.module.system.impl.service.jobService.impl.method.syncJob;

import com.omgservers.model.dto.system.job.SyncJobRequest;
import com.omgservers.model.dto.system.job.SyncJobResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);
}
