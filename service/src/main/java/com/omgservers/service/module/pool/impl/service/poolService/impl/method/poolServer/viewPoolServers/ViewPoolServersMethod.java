package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.viewPoolServers;

import com.omgservers.model.dto.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.model.dto.pool.poolServer.ViewPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServersMethod {
    Uni<ViewPoolServerResponse> viewPoolServers(ViewPoolServersRequest request);
}
