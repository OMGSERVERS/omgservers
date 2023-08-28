package com.omgservers.module.gateway.impl.service.gatewayWebService.impl;

import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.module.gateway.impl.service.gatewayWebService.GatewayWebService;
import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
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
    public Uni<Void> respondMessage(RespondMessageRoutedRequest request) {
        return gatewayService.respondMessage(request);
    }

    @Override
    public Uni<Void> assignPlayer(AssignPlayerRoutedRequest request) {
        return gatewayService.assignPlayer(request);
    }
}