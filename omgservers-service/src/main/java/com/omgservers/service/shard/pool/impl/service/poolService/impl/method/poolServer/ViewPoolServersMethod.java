package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServersMethod {
    Uni<ViewPoolServersResponse> execute(ViewPoolServersRequest request);
}
