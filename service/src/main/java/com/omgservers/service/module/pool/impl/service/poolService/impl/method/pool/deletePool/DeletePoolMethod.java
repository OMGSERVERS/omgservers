package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.deletePool;

import com.omgservers.model.dto.pool.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.pool.DeletePoolResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolMethod {
    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);
}
