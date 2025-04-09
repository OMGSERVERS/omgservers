package com.omgservers.service.server.job.impl.method.syncJob;

import com.omgservers.service.server.job.dto.SyncJobRequest;
import com.omgservers.service.server.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);
}
