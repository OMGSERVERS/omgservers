package com.omgservers.module.system.impl.service.jobService.impl.method.unscheduleJob;

import com.omgservers.dto.internal.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobMethod {
    Uni<Void> unscheduleJob(UnscheduleJobRequest request);
}
