package com.omgservers.base.impl.service.jobSchedulerService;

import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface JobSchedulerService {
    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);
}
