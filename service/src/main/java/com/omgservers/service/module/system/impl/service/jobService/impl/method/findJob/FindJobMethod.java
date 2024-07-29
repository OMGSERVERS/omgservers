package com.omgservers.service.module.system.impl.service.jobService.impl.method.findJob;

import com.omgservers.schema.service.system.job.FindJobRequest;
import com.omgservers.schema.service.system.job.FindJobResponse;
import io.smallrye.mutiny.Uni;

public interface FindJobMethod {
    Uni<FindJobResponse> findJob(FindJobRequest request);
}
