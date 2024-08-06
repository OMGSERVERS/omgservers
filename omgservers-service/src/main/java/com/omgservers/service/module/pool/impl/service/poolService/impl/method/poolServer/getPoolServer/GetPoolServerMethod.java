package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.getPoolServer;

import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolServerMethod {
    Uni<GetPoolServerResponse> getPoolServer(GetPoolServerRequest request);
}
