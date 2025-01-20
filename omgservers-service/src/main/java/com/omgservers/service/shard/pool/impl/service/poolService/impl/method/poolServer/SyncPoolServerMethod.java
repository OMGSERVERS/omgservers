package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolServerMethod {
    Uni<SyncPoolServerResponse> execute(SyncPoolServerRequest request);
}
