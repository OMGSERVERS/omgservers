package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.syncPoolRequest;

import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRequestMethod {
    Uni<SyncPoolRequestResponse> syncPoolRequest(SyncPoolRequestRequest request);
}
