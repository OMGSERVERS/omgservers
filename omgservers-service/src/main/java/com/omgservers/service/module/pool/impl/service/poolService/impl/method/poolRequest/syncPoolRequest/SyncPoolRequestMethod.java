package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.syncPoolRequest;

import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRequestMethod {
    Uni<SyncPoolRequestResponse> syncPoolRequest(SyncPoolRequestRequest request);
}
