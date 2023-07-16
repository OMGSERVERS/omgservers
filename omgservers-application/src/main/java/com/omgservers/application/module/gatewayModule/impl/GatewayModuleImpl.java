package com.omgservers.application.module.gatewayModule.impl;

import com.omgservers.application.module.gatewayModule.GatewayModule;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.GatewayInternalService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayModuleImpl implements GatewayModule {

    final GatewayInternalService gatewayInternalService;

    @Override
    public GatewayInternalService getGatewayInternalService() {
        return gatewayInternalService;
    }
}
