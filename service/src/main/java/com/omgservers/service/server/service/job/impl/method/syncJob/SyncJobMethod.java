package com.omgservers.service.server.service.job.impl.method.syncJob;

import com.omgservers.schema.service.system.job.SyncJobRequest;
import com.omgservers.schema.service.system.job.SyncJobResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);
}
