package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServersMethod {
    Uni<ViewPoolServerResponse> execute(ViewPoolServersRequest request);
}
