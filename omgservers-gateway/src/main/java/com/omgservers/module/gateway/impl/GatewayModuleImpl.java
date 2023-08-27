package com.omgservers.module.gateway.impl;

import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayModuleImpl implements GatewayModule {

    final GatewayService gatewayService;

    public GatewayService getGatewayService() {
        return gatewayService;
    }
}
