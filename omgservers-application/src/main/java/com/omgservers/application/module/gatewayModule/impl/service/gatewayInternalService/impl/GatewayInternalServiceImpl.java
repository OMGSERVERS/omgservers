package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl;

import com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation.GetGatewayServiceApiClientOperation;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.GatewayInternalService;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.assignPlayerMethod.AssignPlayerMethod;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.respondMessageMethod.RespondMessageMethod;
import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayInternalServiceImpl implements GatewayInternalService {

    final RespondMessageMethod respondMessageMethod;
    final AssignPlayerMethod assignPlayerMethod;

    final GetGatewayServiceApiClientOperation getGatewayServiceApiClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<Void> respondMessage(RespondMessageInternalRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return respondMessageMethod.respondMessage(request);
        } else {
            log.info("Request will be routed, targetServer={}, request={}", targetServer, request);
            return getGatewayServiceApiClientOperation.getClient(targetServer)
                    .respondMessage(request);
        }
    }

    @Override
    public Uni<Void> assignPlayer(AssignPlayerInternalRequest request) {
        final var currentServer = getConfigOperation.getConfig().serverUri();
        final var targetServer = request.getServer();
        if (currentServer.equals(targetServer)) {
            return assignPlayerMethod.assignPlayer(request);
        } else {
            log.info("Request will be routed, targetServer={}, request={}", targetServer, request);
            return getGatewayServiceApiClientOperation.getClient(targetServer)
                    .assignPlayer(request);
        }
    }
}
