package com.omgservers.service.server.service.job.impl.method.viewJobs;

import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.job.ViewJobsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewJobsMethod {
    Uni<ViewJobsResponse> viewJobs(ViewJobsRequest request);
}
