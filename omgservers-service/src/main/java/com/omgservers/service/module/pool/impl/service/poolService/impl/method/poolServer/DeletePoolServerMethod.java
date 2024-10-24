package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerMethod {
    Uni<DeletePoolServerResponse> execute(DeletePoolServerRequest request);
}
