package com.omgservers.service.module.gateway.impl.service.gatewayService.impl;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.service.module.gateway.impl.operation.getGatewayModuleClient.GetGatewayModuleClientOperation;
import com.omgservers.service.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.assignClient.AssignClientMethod;
import com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.assignRuntime.AssignRuntimeMethod;
import com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.respondMessage.RespondMessageMethod;
import com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.revokeRuntime.RevokeRuntimeMethod;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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
    final RevokeRuntimeMethod revokeRuntimeMethod;
    final AssignClientMethod assignClientMethod;

    final GetGatewayModuleClientOperation getGatewayModuleClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return respondMessageMethod.respondMessage(request);
        } else {
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .respondMessage(request);
        }
    }

    @Override
    public Uni<AssignClientResponse> assignClient(AssignClientRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return assignClientMethod.assignClient(request);
        } else {
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .assignClient(request);
        }
    }

    @Override
    public Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return assignRuntimeMethod.assignRuntime(request);
        } else {
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .assignRuntime(request);
        }
    }

    @Override
    public Uni<RevokeRuntimeResponse> revokeRuntime(RevokeRuntimeRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return revokeRuntimeMethod.revokeRuntime(request);
        } else {
            return getGatewayModuleClientOperation.getClient(targetServer)
                    .revokeRuntime(request);
        }
    }
}
