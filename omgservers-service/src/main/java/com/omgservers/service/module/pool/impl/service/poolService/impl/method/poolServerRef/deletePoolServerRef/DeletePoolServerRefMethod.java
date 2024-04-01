package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.deletePoolServerRef;

import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerRefMethod {
    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);
}
