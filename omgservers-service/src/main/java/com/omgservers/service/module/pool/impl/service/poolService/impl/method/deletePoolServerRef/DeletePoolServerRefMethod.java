package com.omgservers.service.module.pool.impl.service.poolService.impl.method.deletePoolServerRef;

import com.omgservers.model.dto.pool.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.DeletePoolServerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerRefMethod {
    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);
}
