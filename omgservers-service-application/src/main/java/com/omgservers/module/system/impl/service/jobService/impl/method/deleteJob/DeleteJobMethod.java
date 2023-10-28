package com.omgservers.module.system.impl.service.jobService.impl.method.deleteJob;

import com.omgservers.model.dto.internal.DeleteJobRequest;
import com.omgservers.model.dto.internal.DeleteJobResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);
}
