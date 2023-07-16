package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService;

import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface JobSchedulerService {
    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);
}
