package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignRuntime;

import com.omgservers.dto.gateway.AssignRuntimeRequest;
import io.smallrye.mutiny.Uni;

public interface AssignRuntimeMethod {
    Uni<Void> assignRuntime(AssignRuntimeRequest request);
}
