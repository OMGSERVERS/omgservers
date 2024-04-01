package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.syncPoolServerRef;

import com.omgservers.model.dto.pool.poolServerRef.SyncPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.SyncPoolServerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolServerRefMethod {
    Uni<SyncPoolServerRefResponse> syncPoolServerRef(SyncPoolServerRefRequest request);
}
