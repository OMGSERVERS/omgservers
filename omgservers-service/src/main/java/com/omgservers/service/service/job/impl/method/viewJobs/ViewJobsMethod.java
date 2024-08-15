package com.omgservers.service.service.job.impl.method.viewJobs;

import com.omgservers.service.service.job.dto.ViewJobsRequest;
import com.omgservers.service.service.job.dto.ViewJobsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewJobsMethod {
    Uni<ViewJobsResponse> viewJobs(ViewJobsRequest request);
}
