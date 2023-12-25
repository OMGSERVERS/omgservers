package com.omgservers.service.module.gateway.impl.service.gatewayService;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.gateway.CloseConnectionRequest;
import com.omgservers.model.dto.gateway.CloseConnectionResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface GatewayService {

    Uni<CloseConnectionResponse> closeConnection(@Valid CloseConnectionRequest request);

    Uni<RespondMessageResponse> respondMessage(@Valid RespondMessageRequest request);

    Uni<AssignClientResponse> assignClient(@Valid AssignClientRequest request);

    Uni<AssignRuntimeResponse> assignRuntime(@Valid AssignRuntimeRequest request);

    Uni<RevokeRuntimeResponse> revokeRuntime(@Valid RevokeRuntimeRequest request);
}
