package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.syncPoolServer;

import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolServerMethod {
    Uni<SyncPoolServerResponse> syncPoolServer(SyncPoolServerRequest request);
}
