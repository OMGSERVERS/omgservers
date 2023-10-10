package com.omgservers.module.gateway.impl.service.webService.impl;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.AssignRuntimeResponse;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.module.gateway.impl.service.webService.WebService;
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
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
        return gatewayService.respondMessage(request);
    }

    @Override
    public Uni<AssignClientResponse> assignClient(AssignClientRequest request) {
        return gatewayService.assignClient(request);
    }

    @Override
    public Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request) {
        return gatewayService.assignRuntime(request);
    }

    @Override
    public Uni<RevokeRuntimeResponse> revokeRuntime(RevokeRuntimeRequest request) {
        return gatewayService.revokeRuntime(request);
    }
}
