package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.assignRuntime;

import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface AssignRuntimeMethod {
    Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request);
}
