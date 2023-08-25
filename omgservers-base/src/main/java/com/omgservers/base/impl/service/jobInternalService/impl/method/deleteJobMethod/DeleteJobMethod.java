package com.omgservers.base.impl.service.jobInternalService.impl.method.deleteJobMethod;

import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);
}
