package com.omgservers.module.system.impl.service.jobService.impl.method.scheduleJob;

import com.omgservers.model.dto.system.ScheduleJobRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobMethod {
    Uni<Void> scheduleJob(ScheduleJobRequest request);
}
