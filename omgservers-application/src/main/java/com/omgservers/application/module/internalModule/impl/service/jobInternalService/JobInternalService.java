package com.omgservers.application.module.internalModule.impl.service.jobInternalService;

import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface JobInternalService {
    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);

    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);
}
