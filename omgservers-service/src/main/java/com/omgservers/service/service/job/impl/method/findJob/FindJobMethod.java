package com.omgservers.service.service.job.impl.method.findJob;

import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
import io.smallrye.mutiny.Uni;

public interface FindJobMethod {
    Uni<FindJobResponse> findJob(FindJobRequest request);
}
