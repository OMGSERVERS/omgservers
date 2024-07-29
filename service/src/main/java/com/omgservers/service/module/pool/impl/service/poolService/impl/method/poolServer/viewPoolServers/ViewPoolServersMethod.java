package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.viewPoolServers;

import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServersMethod {
    Uni<ViewPoolServerResponse> viewPoolServers(ViewPoolServersRequest request);
}
