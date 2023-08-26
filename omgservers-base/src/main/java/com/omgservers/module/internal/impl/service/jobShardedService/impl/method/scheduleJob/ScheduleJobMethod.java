package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.scheduleJob;

import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobMethod {
    Uni<Void> scheduleJob(ScheduleJobShardRequest request);
}
