package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.unscheduleJob;

import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobMethod {
    Uni<Void> unscheduleJob(UnscheduleJobShardRequest request);
}
