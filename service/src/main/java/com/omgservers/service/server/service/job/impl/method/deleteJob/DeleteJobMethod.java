package com.omgservers.service.server.service.job.impl.method.deleteJob;

import com.omgservers.schema.service.system.job.DeleteJobRequest;
import com.omgservers.schema.service.system.job.DeleteJobResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);
}
