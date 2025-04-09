package com.omgservers.service.server.job.impl.method.getJob;

import com.omgservers.service.server.job.dto.GetJobRequest;
import com.omgservers.service.server.job.dto.GetJobResponse;
import io.smallrye.mutiny.Uni;

public interface GetJobMethod {
    Uni<GetJobResponse> getJob(GetJobRequest request);
}
