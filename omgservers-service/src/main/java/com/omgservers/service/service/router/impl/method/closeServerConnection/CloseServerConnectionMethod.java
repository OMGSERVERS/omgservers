package com.omgservers.service.service.router.impl.method.closeServerConnection;

import com.omgservers.service.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.service.router.dto.CloseServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseServerConnectionMethod {

    Uni<CloseServerConnectionResponse> closeServerConnection(CloseServerConnectionRequest request);
}
