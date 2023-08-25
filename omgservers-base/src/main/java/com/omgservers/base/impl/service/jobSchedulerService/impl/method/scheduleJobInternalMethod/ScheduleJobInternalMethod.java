package com.omgservers.base.impl.service.jobSchedulerService.impl.method.scheduleJobInternalMethod;

import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobInternalMethod {
    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);
}
