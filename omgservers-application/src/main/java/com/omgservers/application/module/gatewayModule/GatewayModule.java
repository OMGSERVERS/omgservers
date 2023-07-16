package com.omgservers.application.module.gatewayModule;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.GatewayInternalService;

public interface GatewayModule {

    GatewayInternalService getGatewayInternalService();
}
