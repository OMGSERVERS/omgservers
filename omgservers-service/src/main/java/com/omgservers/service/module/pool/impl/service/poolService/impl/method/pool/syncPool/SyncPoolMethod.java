package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.syncPool;

import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolMethod {

    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);
}
