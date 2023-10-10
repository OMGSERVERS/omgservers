package com.omgservers.module.gateway.impl.service.gatewayService;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.AssignRuntimeResponse;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.dto.gateway.RevokeRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GatewayService {

    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);

    Uni<AssignClientResponse> assignClient(AssignClientRequest request);

    Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request);

    Uni<RevokeRuntimeResponse> revokeRuntime(RevokeRuntimeRequest request);
}
