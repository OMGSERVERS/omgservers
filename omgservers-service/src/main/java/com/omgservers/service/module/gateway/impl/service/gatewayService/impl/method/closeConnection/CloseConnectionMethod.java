package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.closeConnection;

import com.omgservers.model.dto.gateway.CloseConnectionRequest;
import com.omgservers.model.dto.gateway.CloseConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseConnectionMethod {
    Uni<CloseConnectionResponse> closeConnection(CloseConnectionRequest request);
}
