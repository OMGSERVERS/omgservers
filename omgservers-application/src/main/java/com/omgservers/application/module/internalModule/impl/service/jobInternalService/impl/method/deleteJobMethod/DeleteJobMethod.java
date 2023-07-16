package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.deleteJobMethod;

import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);
}
