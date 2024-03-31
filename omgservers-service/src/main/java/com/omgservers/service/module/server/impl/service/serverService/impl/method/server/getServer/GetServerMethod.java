package com.omgservers.service.module.server.impl.service.serverService.impl.method.server.getServer;

import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import io.smallrye.mutiny.Uni;

public interface GetServerMethod {
    Uni<GetServerResponse> getServer(GetServerRequest request);
}
