package com.omgservers.module.gateway.impl.service.gatewayService.impl;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.module.gateway.impl.operation.getGatewayModuleClient.GetGatewayModuleClientOperation;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignPlayer.AssignPlayerMethod;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignRuntime.AssignRuntimeMethod;
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
    final AssignRuntimeMethod assignRuntimeMethod;
    final AssignPlayerMethod assignPlayerMethod;

    final GetGatewayModuleClientOperation getGatewayModuleClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
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
    public Uni<Void> assignPlayer(AssignPlayerRequest request) {
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

    @Override
    public Uni<Void> assignRuntime(AssignRuntimeRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return assignRuntimeMethod.assignRuntime(request);
        } else {
            log.info("Request will be routed, targetServer={}, request={}", targetServer, request);
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .assignRuntime(request);
        }
    }
}
