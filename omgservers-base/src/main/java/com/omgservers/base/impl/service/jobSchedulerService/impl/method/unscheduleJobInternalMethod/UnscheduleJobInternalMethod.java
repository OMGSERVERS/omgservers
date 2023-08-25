package com.omgservers.base.impl.service.jobSchedulerService.impl.method.unscheduleJobInternalMethod;

import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobInternalMethod {
    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);
}
