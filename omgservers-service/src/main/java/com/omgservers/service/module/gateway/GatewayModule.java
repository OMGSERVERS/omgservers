package com.omgservers.service.module.gateway;

import com.omgservers.service.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.service.module.gateway.impl.service.gatewayService.GatewayService;

public interface GatewayModule {

    GatewayService getGatewayService();

    ConnectionService getConnectionService();
}
