package com.omgservers.service.module.system.impl.service.jobService.impl.method.unscheduleJob;

import com.omgservers.model.dto.system.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;

public interface UnscheduleJobMethod {
    Uni<Void> unscheduleJob(UnscheduleJobRequest request);
}
