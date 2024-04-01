package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.findPoolServerRef;

import com.omgservers.model.dto.pool.poolServerRef.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.FindPoolServerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolServerRefMethod {
    Uni<FindPoolServerRefResponse> findPoolServerRef(FindPoolServerRefRequest request);
}
