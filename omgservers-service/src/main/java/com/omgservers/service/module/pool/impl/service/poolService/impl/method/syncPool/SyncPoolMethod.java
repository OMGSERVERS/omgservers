package com.omgservers.service.module.pool.impl.service.poolService.impl.method.syncPool;

import com.omgservers.model.dto.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.SyncPoolResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolMethod {

    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);
}
