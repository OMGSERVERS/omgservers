package com.omgservers.module.gateway;

import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;

public interface GatewayModule {

    GatewayService getGatewayService();
}
