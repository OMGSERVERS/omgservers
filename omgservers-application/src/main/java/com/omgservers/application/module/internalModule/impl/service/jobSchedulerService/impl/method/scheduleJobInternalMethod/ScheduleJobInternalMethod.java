package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.method.scheduleJobInternalMethod;

import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobInternalMethod {
    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);
}
