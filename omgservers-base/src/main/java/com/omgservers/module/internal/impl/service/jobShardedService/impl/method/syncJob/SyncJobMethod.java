package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob;

import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request);
}
