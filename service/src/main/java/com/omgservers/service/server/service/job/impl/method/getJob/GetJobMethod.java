package com.omgservers.service.server.service.job.impl.method.getJob;

import com.omgservers.schema.service.system.job.GetJobRequest;
import com.omgservers.schema.service.system.job.GetJobResponse;
import io.smallrye.mutiny.Uni;

public interface GetJobMethod {
    Uni<GetJobResponse> getJob(GetJobRequest request);
}
