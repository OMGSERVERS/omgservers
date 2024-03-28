package com.omgservers.service.module.pool.impl.service.poolService.impl.method.deletePool;

import com.omgservers.model.dto.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.DeletePoolResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolMethod {
    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);
}
