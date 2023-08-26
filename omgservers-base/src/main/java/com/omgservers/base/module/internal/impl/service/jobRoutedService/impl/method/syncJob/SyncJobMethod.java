package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.syncJob;

import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncJobMethod {
    Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request);
}
