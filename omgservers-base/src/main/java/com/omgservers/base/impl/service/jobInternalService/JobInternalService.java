package com.omgservers.base.impl.service.jobInternalService;

import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import io.smallrye.mutiny.Uni;

public interface JobInternalService {
    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);

    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);
}
