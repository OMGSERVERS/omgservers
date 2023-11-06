package com.omgservers.service.module.system.impl.service.jobService.impl.method.getJob;

import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import io.smallrye.mutiny.Uni;

public interface GetJobMethod {
    Uni<GetJobResponse> getJob(GetJobRequest request);
}
