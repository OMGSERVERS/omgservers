package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.scheduleJob;

import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface ScheduleJobMethod {
    Uni<Void> scheduleJob(ScheduleJobRoutedRequest request);
}
