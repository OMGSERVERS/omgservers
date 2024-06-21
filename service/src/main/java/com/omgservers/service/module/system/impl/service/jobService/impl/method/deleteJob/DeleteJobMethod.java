package com.omgservers.service.module.system.impl.service.jobService.impl.method.deleteJob;

import com.omgservers.model.dto.system.job.DeleteJobRequest;
import com.omgservers.model.dto.system.job.DeleteJobResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);
}
