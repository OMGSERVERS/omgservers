package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import io.smallrye.mutiny.Uni;

public interface AssignClientMethod {
    Uni<AssignClientResponse> assignClient(AssignClientRequest request);
}
