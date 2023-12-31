package com.omgservers.service.module.gateway.impl.service.webService.impl;

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
import com.omgservers.service.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.service.module.gateway.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final GatewayService gatewayService;

    @Override
    public Uni<CloseConnectionResponse> closeConnection(final CloseConnectionRequest request) {
        return gatewayService.closeConnection(request);
    }

    @Override
    public Uni<RespondMessageResponse> respondMessage(final RespondMessageRequest request) {
        return gatewayService.respondMessage(request);
    }

    @Override
    public Uni<AssignClientResponse> assignClient(final AssignClientRequest request) {
        return gatewayService.assignClient(request);
    }

    @Override
    public Uni<AssignRuntimeResponse> assignRuntime(final AssignRuntimeRequest request) {
        return gatewayService.assignRuntime(request);
    }

    @Override
    public Uni<RevokeRuntimeResponse> revokeRuntime(final RevokeRuntimeRequest request) {
        return gatewayService.revokeRuntime(request);
    }
}
