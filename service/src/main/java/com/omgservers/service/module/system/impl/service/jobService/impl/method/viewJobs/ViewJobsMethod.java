package com.omgservers.service.module.system.impl.service.jobService.impl.method.viewJobs;

import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.job.ViewJobsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewJobsMethod {
    Uni<ViewJobsResponse> viewJobs(ViewJobsRequest request);
}
