package com.omgservers.service.module.server.impl;

import com.omgservers.service.module.server.ServerModule;
import com.omgservers.service.module.server.impl.service.serverService.ServerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ServerModuleImpl implements ServerModule {

    final ServerService serverService;

    public ServerService getServerService() {
        return serverService;
    }
}
