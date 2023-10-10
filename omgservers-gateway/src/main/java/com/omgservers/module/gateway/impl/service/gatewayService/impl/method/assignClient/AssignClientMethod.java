package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import io.smallrye.mutiny.Uni;

public interface AssignClientMethod {
    Uni<AssignClientResponse> assignClient(AssignClientRequest request);
}
