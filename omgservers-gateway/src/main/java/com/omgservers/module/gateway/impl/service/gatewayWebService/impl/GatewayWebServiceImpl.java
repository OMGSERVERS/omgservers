package com.omgservers.module.gateway.impl.service.gatewayWebService.impl;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.module.gateway.impl.service.gatewayWebService.GatewayWebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayWebServiceImpl implements GatewayWebService {

    final GatewayService gatewayService;

    @Override
    public Uni<Void> respondMessage(RespondMessageRequest request) {
        return gatewayService.respondMessage(request);
    }

    @Override
    public Uni<Void> assignPlayer(AssignPlayerRequest request) {
        return gatewayService.assignPlayer(request);
    }

    @Override
    public Uni<Void> assignRuntime(AssignRuntimeRequest request) {
        return gatewayService.assignRuntime(request);
    }
}