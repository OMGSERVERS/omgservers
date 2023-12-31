package com.omgservers.service.module.gateway.impl;

import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.service.module.gateway.impl.service.gatewayService.GatewayService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayModuleImpl implements GatewayModule {

    final ConnectionService connectionService;
    final GatewayService gatewayService;

    public GatewayService getGatewayService() {
        return gatewayService;
    }

    @Override
    public ConnectionService getConnectionService() {
        return connectionService;
    }
}
