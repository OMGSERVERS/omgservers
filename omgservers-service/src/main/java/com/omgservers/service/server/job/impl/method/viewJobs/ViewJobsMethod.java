package com.omgservers.service.server.job.impl.method.viewJobs;

import com.omgservers.service.server.job.dto.ViewJobsRequest;
import com.omgservers.service.server.job.dto.ViewJobsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewJobsMethod {
    Uni<ViewJobsResponse> viewJobs(ViewJobsRequest request);
}
