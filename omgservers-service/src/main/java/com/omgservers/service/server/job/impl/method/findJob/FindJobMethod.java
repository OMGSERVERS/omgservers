package com.omgservers.service.server.job.impl.method.findJob;

import com.omgservers.service.server.job.dto.FindJobRequest;
import com.omgservers.service.server.job.dto.FindJobResponse;
import io.smallrye.mutiny.Uni;

public interface FindJobMethod {
    Uni<FindJobResponse> findJob(FindJobRequest request);
}
