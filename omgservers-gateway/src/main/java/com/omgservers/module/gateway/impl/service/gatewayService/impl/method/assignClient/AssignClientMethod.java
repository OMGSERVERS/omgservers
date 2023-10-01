package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.dto.gateway.AssignClientRequest;
import io.smallrye.mutiny.Uni;

public interface AssignClientMethod {
    Uni<Void> assignClient(AssignClientRequest request);
}
