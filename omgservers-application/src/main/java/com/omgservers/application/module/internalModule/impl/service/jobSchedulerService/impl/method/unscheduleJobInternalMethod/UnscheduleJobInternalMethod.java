package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.method.unscheduleJobInternalMethod;

import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobInternalMethod {
    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);
}
