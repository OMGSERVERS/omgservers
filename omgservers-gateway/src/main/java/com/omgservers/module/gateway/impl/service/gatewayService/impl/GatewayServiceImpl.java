package com.omgservers.module.gateway.impl.service.gatewayService.impl;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import com.omgservers.module.gateway.impl.operation.getGatewayModuleClient.GetGatewayModuleClientOperation;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignPlayer.AssignPlayerMethod;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage.RespondMessageMethod;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayServiceImpl implements GatewayService {

    final RespondMessageMethod respondMessageMethod;
    final AssignPlayerMethod assignPlayerMethod;

    final GetGatewayModuleClientOperation getGatewayModuleClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<Void> respondMessage(RespondMessageRoutedRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return respondMessageMethod.respondMessage(request);
        } else {
            log.info("Request will be routed, targetServer={}, request={}", targetServer, request);
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .respondMessage(request);
        }
    }

    @Override
    public Uni<Void> assignPlayer(AssignPlayerRoutedRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return assignPlayerMethod.assignPlayer(request);
        } else {
            log.info("Request will be routed, targetServer={}, request={}", targetServer, request);
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .assignPlayer(request);
        }
    }
}
