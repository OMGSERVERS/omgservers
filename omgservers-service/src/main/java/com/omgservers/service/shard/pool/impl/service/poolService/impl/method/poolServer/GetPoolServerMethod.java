package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolServerMethod {
    Uni<GetPoolServerResponse> execute(GetPoolServerRequest request);
}
