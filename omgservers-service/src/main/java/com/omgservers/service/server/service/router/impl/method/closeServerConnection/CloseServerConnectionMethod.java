package com.omgservers.service.server.service.router.impl.method.closeServerConnection;

import com.omgservers.service.server.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.CloseServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseServerConnectionMethod {

    Uni<CloseServerConnectionResponse> closeServerConnection(CloseServerConnectionRequest request);
}
