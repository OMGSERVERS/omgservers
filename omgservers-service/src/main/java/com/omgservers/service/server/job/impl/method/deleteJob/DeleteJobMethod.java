package com.omgservers.service.server.job.impl.method.deleteJob;

import com.omgservers.service.server.job.dto.DeleteJobRequest;
import com.omgservers.service.server.job.dto.DeleteJobResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);
}
