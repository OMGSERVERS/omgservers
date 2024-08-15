package com.omgservers.service.service.job.impl.method.getJob;

import com.omgservers.service.service.job.dto.GetJobRequest;
import com.omgservers.service.service.job.dto.GetJobResponse;
import io.smallrye.mutiny.Uni;

public interface GetJobMethod {
    Uni<GetJobResponse> getJob(GetJobRequest request);
}
