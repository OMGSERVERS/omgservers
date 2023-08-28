package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob;

import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request);
}
