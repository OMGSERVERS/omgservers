package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.revokeRuntime;

import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface RevokeRuntimeMethod {
    Uni<RevokeRuntimeResponse> revokeRuntime(RevokeRuntimeRequest request);
}
