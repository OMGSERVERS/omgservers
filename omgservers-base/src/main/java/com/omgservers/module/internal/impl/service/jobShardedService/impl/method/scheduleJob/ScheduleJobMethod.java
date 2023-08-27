package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.scheduleJob;

import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobMethod {
    Uni<Void> scheduleJob(ScheduleJobShardedRequest request);
}
